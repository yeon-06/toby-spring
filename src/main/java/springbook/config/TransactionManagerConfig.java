package springbook.config;

import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerConfig {

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new JdbcTransactionManager(dataSource());
    }

    private DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:13306/springbook");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");

        return dataSource;
    }
}
