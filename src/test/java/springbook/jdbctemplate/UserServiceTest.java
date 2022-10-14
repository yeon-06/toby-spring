package springbook.jdbctemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.dao.UserDao;
import springbook.jdbctemplate.dao.UserDaoImpl;
import springbook.jdbctemplate.service.TxUserService;
import springbook.jdbctemplate.service.UserServiceImpl;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class, UserDaoImpl.class, UserServiceImpl.class, TxUserService.class})
class UserServiceTest {

    @Autowired
    private TxUserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void delete() {
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

        // when
        userService.upgradeLevels(users);

        // then
        User actual1 = userDao.findById(user1.getId());
        User actual2 = userDao.findById(user2.getId());
        User actual3 = userDao.findById(user3.getId());

        assertThat(actual1.getLevel()).isEqualTo(Level.BASIC);
        assertThat(actual2.getLevel()).isEqualTo(Level.BASIC);
        assertThat(actual3.getLevel()).isEqualTo(Level.GOLD);
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

    @DisplayName(value = "사용자 레벨 업그레이드 실패")
    @Test
    void upgrade_failed() {
        // given
        String id = "yeonlog06";
        User user = new User(id, "연로그", "1234", Level.GOLD);
        userDao.save(user);

        // when & then
        assertThatThrownBy(() -> userService.upgradeLevel(user))
                .isInstanceOf(IllegalStateException.class);
    }

    private void saveAll(final List<User> users) {
        for (User user : users) {
            userDao.save(user);
        }
    }
}
