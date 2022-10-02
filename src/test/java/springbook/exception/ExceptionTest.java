package springbook.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ExceptionTest {

    @DisplayName(value = "예외 회피")
    @Test
    void avoid() {
        // given & when & then
        assertThatThrownBy(() -> checkNaturalNumber(-1))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName(value = "예외 전환")
    @Test
    void change() {
        // given & when & then
        assertThatThrownBy(this::changeException)
                .isInstanceOf(IllegalArgumentException.class);
    }

    void changeException() {
        try {
            checkNaturalNumber(-1);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 인자를 넘겼습니다.");
        }
    }

    @DisplayName(value = "예외 복구")
    @Test
    void recovery() {
        // given & when & then
        assertDoesNotThrow(this::recoverException);
    }

    void recoverException() {
        try {
            checkNaturalNumber(-1);
        } catch (Exception e) {
            // 정상 처리
            checkNaturalNumber(1);
        }
    }

    void checkNaturalNumber(final int number) throws RuntimeException {
        if (number <= 0) {
            throw new RuntimeException();
        }
    }
}
