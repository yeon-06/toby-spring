package springbook.jdbc;

import java.sql.Connection;

public interface ConnectionMaker {

    Connection getConnection();
}
