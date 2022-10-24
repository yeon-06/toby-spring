package springbook.jdbctemplate.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.domain.User;
import springbook.jdbctemplate.dao.StubUserDao;
import springbook.jdbctemplate.dao.UserDao;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StubUserDao.class, UserServiceImpl.class})
class UserServiceWithStubDaoTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void delete() {
        userDao.deleteAll();
    }

    @DisplayName(value = "사용자 레벨 정상 업그레이드")
    @Test
    void upgrade_success() {
        // given
        String id = "yeonlog06";
        User user = new User(id, "연로그", "1234", Level.BASIC);
        userDao.save(user);

        // when
        userService.upgradeLevel(user);

        // then
        User actual = userDao.findById(id);
        assertThat(actual.getLevel()).isEqualTo(Level.SILVER);
    }
}
