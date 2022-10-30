package springbook.jdbctemplate.supporter;

public interface SqlReader {
    void readSql(SqlRegistry sqlRegistry) throws Exception;
}
