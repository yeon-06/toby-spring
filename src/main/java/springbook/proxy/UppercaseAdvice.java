package springbook.proxy;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UppercaseAdvice implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof String) {
            return ((String) result).toUpperCase();
        }
        return result;
    }
}
