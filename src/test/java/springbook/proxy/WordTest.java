package springbook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

class WordTest {

    @DisplayName(value = "ProxyWord를 이용한 word 테스트")
    @Test
    void proxyWord() {
        // given
        Word word = new ProxyWord(new YeonlogWord());

        // when & then
        assertThat(word.sayHello()).isEqualTo("HELLO YEONLOG!");
        assertThat(word.sayThanks()).isEqualTo("THANK YOU YEONLOG!");
    }

    @DisplayName(value = "InvocationHandler를 이용해 다이나믹 프록시 생성")
    @Test
    void dynamicProxy() {
        // given
        Word proxy = (Word) Proxy.newProxyInstance(
                getClass().getClassLoader(),                // 다이나믹 프록시가 정의되는 클래스 로더
                new Class[]{Word.class},                    // 다이나믹 프록시가 구현해야 하는 인터페이스 (여러개 가능)
                new UppercaseHandler(new YeonlogWord())     // InvocationHandler 구현 오브젝트
        );

        // when & then
        assertThat(proxy.sayHello()).isEqualTo("HELLO YEONLOG!");
        assertThat(proxy.sayThanks()).isEqualTo("THANK YOU YEONLOG!");
    }

    @DisplayName(value = "ProxyFactoryBean를 이용해 다이나믹 프록시 생성")
    @Test
    void proxyFactoryBean() {
        // given
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new YeonlogWord());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Word proxy = (Word) proxyFactoryBean.getObject();

        // when & then
        assertThat(proxy.sayHello()).isEqualTo("HELLO YEONLOG!");
        assertThat(proxy.sayThanks()).isEqualTo("THANK YOU YEONLOG!");
    }
}
