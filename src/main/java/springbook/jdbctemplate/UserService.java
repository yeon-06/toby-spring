package springbook.jdbctemplate;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class UserService {

    private final UserDao userDao;
    private final DataSource dataSource;

    public UserService(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.userDao = new UserDao(dataSource);
    }

    public void upgradeLevels(final List<User> users) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
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
