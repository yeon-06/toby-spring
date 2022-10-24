package springbook.jdbctemplate.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class User {

    private final String id;
    private final String name;
    private final String password;
    private Level level;

    public User(final String id, final String name, final String password, final Level level) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
    }

    public void upgradeLevel() {
        level = level.nextLevel();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }
}
