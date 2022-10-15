package springbook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import springbook.proxy.domain.Word;

public class UppercaseHandler implements InvocationHandler {

    private final Word target;

    public UppercaseHandler(final Word target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object result = method.invoke(target, args);   // 핵심 로직
        if (result instanceof String) {
            return ((String) result).toUpperCase();    // 부가 기능
        }
        return result;
    }
}
