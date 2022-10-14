package springbook.jdbctemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springbook.jdbctemplate.dao.UserDao;
import springbook.jdbctemplate.service.UserService;
import springbook.jdbctemplate.service.UserServiceImpl;


class UserServiceWithMockTest {

    @DisplayName(value = "UserDao 모킹 테스트")
    @Test
    void verify_upgrade() {
        // given
        UserDao userDao = mock(UserDao.class);
        UserService userService = new UserServiceImpl(userDao);

        User user = new User("yeon-06", "연로그", "1234", Level.BASIC);

        // when
        userService.upgradeLevel(user);

        // then
        verify(userDao).update(any(User.class));
    }
}
