package springbook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcut;

class ProxyFactoryBeanTest {

    @DisplayName(value = "ProxyFactoryBean을 이용한 proxy 체크")
    @Test
    void pointcut() {
        // given
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setClassFilter(clazz -> clazz.getSimpleName().startsWith("Yeonlog"));
        pointcut.setMappedName("sayH*");

        validateAdvice(new YeonlogWord(), pointcut, true);
        validateAdvice(new LogyeonWord(), pointcut, false);
    }

    private void validateAdvice(final Object target, final Pointcut pointcut, boolean isAdvice) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.addAdvisor(new UppercaseAdvisor(pointcut, new UppercaseAdvice()));

        Word proxy = (Word) proxyFactoryBean.getObject();

        // when & then
        if (isAdvice) {
            assertThat(proxy.sayHello()).startsWith("HELLO ");
            assertThat(proxy.sayThanks()).startsWith("Thank you ");
            return;
        }

        assertThat(proxy.sayHello()).startsWith("Hello ");
        assertThat(proxy.sayThanks()).startsWith("Thank you ");
    }
}
