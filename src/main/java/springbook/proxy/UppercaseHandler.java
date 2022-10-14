package springbook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private final Word target;

    public UppercaseHandler(final Word target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);   // 핵심 로직
        return result.toUpperCase();                            // 부가 기능
    }
}
