package springbook.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy {

    private final User user;

    public AddStatement(final User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(final Connection connection) throws SQLException {
        String sql = "insert into users(id, name, password) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        return preparedStatement;
    }
}
