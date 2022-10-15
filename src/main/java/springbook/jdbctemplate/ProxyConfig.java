package springbook.jdbctemplate;

import javax.sql.DataSource;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springbook.jdbctemplate.service.UserService;
import springbook.jdbctemplate.service.UserServiceImpl;

@Configuration
@Import(value = {DataSourceConfig.class, UserServiceImpl.class})
public class ProxyConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(userService);
        factoryBean.addAdvisor(pointcutAdvisor());
        return factoryBean;
    }

    private DefaultPointcutAdvisor pointcutAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    private Pointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    private Advice transactionAdvice() {
        return new TransactionAdvice(dataSource);
    }
}
