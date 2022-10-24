package springbook.jdbctemplate.service;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.jdbctemplate.domain.User;

@Component
public class TxUserService implements UserService {

    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    public TxUserService(final UserServiceImpl userService, final DataSource dataSource) {
        this.userService = userService;
        this.transactionManager = new JdbcTransactionManager(dataSource);
    }

    @Override
    public void upgradeLevels(final List<User> users) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.upgradeLevels(users);
            transactionManager.commit(status);
        } catch (IllegalStateException e) {
            transactionManager.rollback(status);
        }
    }

    @Override
    public void upgradeLevel(final User user) {
        userService.upgradeLevel(user);
    }
}
