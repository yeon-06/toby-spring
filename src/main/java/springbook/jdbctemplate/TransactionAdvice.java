package springbook.jdbctemplate;

import javax.sql.DataSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TransactionAdvice implements MethodInterceptor {

    private final PlatformTransactionManager transactionManager;

    public TransactionAdvice(final DataSource dataSource) {
        this.transactionManager = new JdbcTransactionManager(dataSource);
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object result = invocation.proceed();
            transactionManager.commit(status);
            return result;

        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
