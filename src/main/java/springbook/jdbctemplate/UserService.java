package springbook.jdbctemplate;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class UserService {

    private final UserDao userDao;
    private final DataSource dataSource;

    public UserService(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.userDao = new UserDao(dataSource);
    }

    public void upgradeLevels(final List<User> users) throws Exception {
        TransactionSynchronizationManager.initSynchronization();            // 동기화 작업 초기화
        Connection connection = DataSourceUtils.getConnection(dataSource);  // DB 커넥션 생성 & 동기화 (=트랜잭션 시작)
        try {
            connection.setAutoCommit(false);
            for (User user : users) {
                upgradeLevel(user);
            }
            connection.commit();
        } catch (IllegalStateException e) {
            connection.rollback();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);      // Connection 안전하게 close
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    public void upgradeLevel(final User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
