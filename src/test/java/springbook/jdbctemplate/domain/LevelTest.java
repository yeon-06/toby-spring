package springbook.jdbctemplate.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import springbook.jdbctemplate.domain.Level;

class LevelTest {

    @ParameterizedTest(name = "{0}의 Level은 {1}")
    @CsvSource(value = {"0,BASIC", "1,BASIC", "2,SILVER", "3,GOLD"})
    void of(final int value, final Level expected) {
        // given & when
        Level level = Level.of(value);

        // then
        assertThat(level).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}의 다음 Level은 {1}")
    @CsvSource(value = {"BASIC, SILVER", "SILVER,GOLD"})
    void nextLevel(final Level level, final Level expected) {
        // given & when
        Level nextLevel = level.nextLevel();

        // then
        assertThat(nextLevel).isEqualTo(expected);
    }

    @DisplayName(value = "업그레이드 불가능한 레벨")
    @Test
    void cannotUpgrade() {
        // given
        Level level = Level.GOLD;

        // when & then
        assertThatThrownBy(level::nextLevel)
                .isInstanceOf(IllegalStateException.class);
    }
}
