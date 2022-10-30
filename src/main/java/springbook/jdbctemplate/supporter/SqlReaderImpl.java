package springbook.jdbctemplate.supporter;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class SqlReaderImpl implements SqlReader {

    private static final String DEFAULT_RESOURCE = "sql-user.properties";

    private String resource = DEFAULT_RESOURCE;

    @Override
    public void setResource(final String resource) {
        this.resource = resource;
    }

    @Override
    public void readSql(final SqlRegistry sqlRegistry) throws Exception {
        Properties properties = new Properties();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        properties.load(inputStream);

        Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();

        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = properties.getProperty(key);
            sqlRegistry.registerSql(key, value);
        }
    }
}
