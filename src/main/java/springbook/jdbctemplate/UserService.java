package springbook.jdbctemplate;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels(final List<User> users) {
        PlatformTransactionManager transactionManager = new JtaTransactionManager();
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            for (User user : users) {
                upgradeLevel(user);
            }
            transactionManager.commit(status);
        } catch (IllegalStateException e) {
            transactionManager.rollback(status);
        }
    }

    public void upgradeLevel(final User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
