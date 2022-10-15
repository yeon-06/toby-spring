package springbook.jdbctemplate.domain;

import java.util.Arrays;

public enum Level {

    BASIC(1),
    SILVER(2),
    GOLD(3),
    ;

    private final int value;

    Level(final int value) {
        this.value = value;
    }

    public static Level of(final int level) {
        return Arrays.stream(Level.values())
                .filter(it -> it.value == level)
                .findAny()
                .orElse(BASIC);
    }

    public Level nextLevel() {
        if (this == GOLD) {
            throw new IllegalStateException("더 업그레이드할 수 없는 레벨입니다.");
        }
        return of(value + 1);
    }

    public int getValue() {
        return value;
    }
}
