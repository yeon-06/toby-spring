package springbook.jdbctemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TransactionHandler implements InvocationHandler {

    private final Object target;
    private final PlatformTransactionManager transactionManager;

    public TransactionHandler(final Object target, final PlatformTransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object result = method.invoke(target, args);
            transactionManager.commit(status);
            return result;

        } catch (InvocationTargetException e) {
            transactionManager.rollback(status);
            throw e.getTargetException();
        }
    }
}
