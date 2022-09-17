package springbook.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.config.DaoConfig;

class UserDaoTest {

    private static UserDao userDao;

    @BeforeAll
    static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoConfig.class);
        userDao = applicationContext.getBean(UserDao.class);
    }

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
