package springbook.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{

    @Override
    public PreparedStatement makePreparedStatement(final Connection connection) throws SQLException {
        return connection.prepareStatement("delete from users");
    }
}
