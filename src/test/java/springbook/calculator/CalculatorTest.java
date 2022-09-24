package springbook.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @DisplayName(value = "file 숫자들의 합")
    @Test
    void sum() {
        // given
        URL url = getClass().getResource("/numbers.txt");
        String path = Objects.requireNonNull(url).getPath();
        Calculator calculator = new Calculator(path);

        // when
        int sum = calculator.sum();

        // then
        assertThat(sum).isEqualTo(6);
    }
}
