package springbook.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class JdbcContext {

    private final DataSource dataSource;

    public JdbcContext(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(final String query, final Object... args) {
        execute(connection -> connection.prepareStatement(query), args);
    }

    private void execute(final StatementStrategy statementStrategy, final Object[] params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = statementStrategy.makePreparedStatement(connection)) {
            setParams(params, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParams(final Object[] params, final PreparedStatement preparedStatement) throws SQLException {
        int length = params.length;
        for (int i = 0; i < length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
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
                        resultSet.getString("password"),
                        resultSet.getInt("level")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
