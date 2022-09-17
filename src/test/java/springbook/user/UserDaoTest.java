package springbook.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private static final UserDao userDao = new UserDao();

    @BeforeEach
    void delete() {
        userDao.deleteAll();
    }

    @DisplayName(value = "사용자 조회하기")
    @Test
    void findById() {
        // given
        User user = new User("id", "name", "1234");
        userDao.add(user);

        // when
        User actual = userDao.findById(user.getId());

        // then
        assertThat(actual).isEqualTo(user);
    }
}
