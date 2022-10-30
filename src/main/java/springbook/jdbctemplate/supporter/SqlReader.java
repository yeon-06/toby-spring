package springbook.jdbctemplate.supporter;

public interface SqlReader {

    void setResource(final String resource);
    void readSql(SqlRegistry sqlRegistry) throws Exception;
}
