package springbook.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import springbook.user.User;

public class JdbcContext {

    private final DataSource dataSource;

    public JdbcContext(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(final String query) {
        execute(connection -> connection.prepareStatement(query));
    }

    public void execute(final StatementStrategy statementStrategy) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = statementStrategy.makePreparedStatement(connection)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User executeSelect(final StatementStrategy statementStrategy) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = statementStrategy.makePreparedStatement(connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
