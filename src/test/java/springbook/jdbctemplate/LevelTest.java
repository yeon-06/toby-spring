package springbook.jdbctemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LevelTest {

    @ParameterizedTest(name = "{0}의 Level은 {1}")
    @CsvSource(value = {"0,BASIC", "1,BASIC", "2,SILVER", "3,GOLD"})
    void of(final int value, final Level expected) {
        // given & when
        Level level = Level.of(value);

        // then
        assertThat(level).isEqualTo(expected);
    }
}
