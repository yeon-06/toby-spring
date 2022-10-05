package springbook.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private final JdbcContext jdbcContext;

    public UserDao(final JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) {
        jdbcContext.executeSql("insert into users(id, name, password) values(?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User findById(String id) {
        StatementStrategy statementStrategy = connection -> {
            String sql = "select id, name, password from users where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            return preparedStatement;
        };

        return jdbcContext.executeSelect(statementStrategy);
    }

    public void deleteAll() {
        jdbcContext.executeSql("delete from users");
    }
}
