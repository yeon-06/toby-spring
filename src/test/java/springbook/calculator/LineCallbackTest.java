package springbook.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LineCallbackTest {

    @DisplayName(value = "제네릭 타입 테스트")
    @Test
    void generic() {
        // given
        LineCallback<String> callback = (line, value) -> line + value;

        // when
        String actual = callback.executeWithLine("aaa", "bbb");

        // then
        assertThat(actual).isEqualTo("aaabbb");
    }
}
