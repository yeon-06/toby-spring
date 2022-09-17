package springbook.user;

import java.sql.Connection;

public interface ConnectionMaker {

    Connection getConnection();
}
