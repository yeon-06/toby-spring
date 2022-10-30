package springbook.jdbctemplate.supporter;

import org.springframework.stereotype.Component;

@Component
public class SqlFinder implements SqlService {

    private SqlRegistry sqlRegistry;

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlRegistry = new SqlRegistryImpl();
        new SqlReaderImpl().readSql(sqlRegistry);
    }

    @Override
    public String getSql(final String key) {
        return sqlRegistry.findSql(key);
    }
}
