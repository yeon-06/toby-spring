package springbook.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import springbook.jdbc.JdbcContext;
import springbook.jdbc.StatementStrategy;

public class UserDao {

    private final JdbcContext jdbcContext;

    public UserDao(final JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) {
        StatementStrategy statementStrategy = connection -> {
            String sql = "insert into users(id, name, password) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            return preparedStatement;
        };

        jdbcContext.execute(statementStrategy);
    }

    public User findById(String id) {
        StatementStrategy statementStrategy = new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(final Connection connection) throws SQLException {
                String sql = "select id, name, password from users where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, id);
                return preparedStatement;
            }
        };

        return jdbcContext.executeSelect(statementStrategy);
    }

    public void deleteAll() {
        StatementStrategy statementStrategy = connection -> connection.prepareStatement("delete from users");

        jdbcContext.execute(statementStrategy);
    }
}
