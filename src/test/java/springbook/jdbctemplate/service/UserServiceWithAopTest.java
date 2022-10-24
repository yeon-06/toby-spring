package springbook.jdbctemplate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.ProxyConfig;
import springbook.jdbctemplate.dao.UserDao;
import springbook.jdbctemplate.dao.UserDaoImpl;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.domain.User;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProxyConfig.class, UserDaoImpl.class})
class UserServiceWithAopTest {

    @Autowired
    private ProxyFactoryBean proxyFactoryBean;

    @Autowired
    private UserDao userDao;

    private UserService userService;

    @BeforeEach
    void delete() {
        userService = (UserService) proxyFactoryBean.getObject();
        userDao.deleteAll();
    }

    @DisplayName(value = "여러명의 사용자 레벨 정상 업그레이드")
    @Test
    void upgradeLevels_succeed() {
        // given
        User user1 = new User("id1", "name", "password", Level.BASIC);
        User user2 = new User("id2", "name", "password", Level.BASIC);
        User user3 = new User("id3", "name", "password", Level.SILVER);
        List<User> users = List.of(user1, user2, user3);
        saveAll(users);

        // when
        userService.upgradeLevels(users);

        // then
        User actual1 = userDao.findById(user1.getId());
        User actual2 = userDao.findById(user2.getId());
        User actual3 = userDao.findById(user3.getId());

        assertThat(actual1.getLevel()).isEqualTo(Level.SILVER);
        assertThat(actual2.getLevel()).isEqualTo(Level.SILVER);
        assertThat(actual3.getLevel()).isEqualTo(Level.GOLD);
    }

    @DisplayName(value = "하나라도 레벨 정상 업그레이드 되지 않으면 전부 롤백")
    @Test
    void upgradeLevels_failed() {
        // given
        User user1 = new User("id1", "name", "password", Level.BASIC);
        User user2 = new User("id2", "name", "password", Level.BASIC);
        User user3 = new User("id3", "name", "password", Level.GOLD);
        List<User> users = List.of(user1, user2, user3);
        saveAll(users);

        // when & then
        assertThatThrownBy(() -> userService.upgradeLevels(users))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("더 업그레이드할 수 없는 레벨입니다.");

        User actual1 = userDao.findById(user1.getId());
        User actual2 = userDao.findById(user2.getId());
        User actual3 = userDao.findById(user3.getId());

        assertThat(actual1.getLevel()).isEqualTo(Level.BASIC);
        assertThat(actual2.getLevel()).isEqualTo(Level.BASIC);
        assertThat(actual3.getLevel()).isEqualTo(Level.GOLD);
    }

    private void saveAll(final List<User> users) {
        for (User user : users) {
            userDao.save(user);
        }
    }
}
