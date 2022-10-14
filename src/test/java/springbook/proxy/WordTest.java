package springbook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
