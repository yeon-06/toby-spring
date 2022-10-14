package springbook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WordFactory.class)
class WordFactoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(value = "factory bean에서 Word 가져오기")
    @Test
    void name() {
        // given & when
        Word word = applicationContext.getBean(Word.class);

        // then
        assertThat(word).isInstanceOf(Word.class);
        assertThat(word.sayHello()).isEqualTo("HELLO YEONLOG!");
    }
}
