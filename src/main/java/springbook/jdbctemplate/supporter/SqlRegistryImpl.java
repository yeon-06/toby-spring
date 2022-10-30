package springbook.jdbctemplate.supporter;

import java.util.HashMap;
import java.util.Map;

public class SqlRegistryImpl implements SqlRegistry {

    private final Map<String, String> map = new HashMap<>();

    @Override
    public void registerSql(final String key, final String sql) {
        map.put(key, sql);
    }

    @Override
    public String findSql(final String key) {
        return map.get(key);
    }
}
