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
        jdbcContext.executeSql("insert into users(id, name, password, level) values(?,?,?,?)",
                user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue());
    }

    public User findById(String id) {
        StatementStrategy statementStrategy = connection -> {
            String sql = "select id, name, password, level from users where id = ?";
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
