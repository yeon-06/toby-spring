package springbook.jdbctemplate.supporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SqlRegistryImpl implements SqlRegistry {

    private final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void registerSql(final String key, final String sql) {
        map.put(key, sql);
    }

    @Override
    public String findSql(final String key) {
        return map.get(key);
    }
}
