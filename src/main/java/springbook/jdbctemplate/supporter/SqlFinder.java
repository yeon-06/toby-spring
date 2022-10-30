package springbook.jdbctemplate.supporter;

import org.springframework.stereotype.Component;

@Component
public class SqlFinder implements SqlService {

    private final SqlRegistry sqlRegistry;
    private final SqlReader sqlReader;

    public SqlFinder(final SqlRegistry sqlRegistry, final SqlReader sqlReader) {
        this.sqlRegistry = sqlRegistry;
        this.sqlReader = sqlReader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlReader.readSql(sqlRegistry);
    }

    @Override
    public String getSql(final String key) {
        return sqlRegistry.findSql(key);
    }
}
