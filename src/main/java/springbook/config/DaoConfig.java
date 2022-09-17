package springbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.ConnectionMaker;
import springbook.user.SimpleConnection;
import springbook.user.UserDao;

@Configuration
public class DaoConfig {

    @Bean
    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    private ConnectionMaker getConnectionMaker() {
        return new SimpleConnection();
    }
}
