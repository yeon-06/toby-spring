package springbook.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.domain.Level;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void delete() {
        userDao.deleteAll();
    }

    @DisplayName(value = "사용자 조회하기")
    @Test
    void findById() {
        // given
        User user = new User("id", "name", "1234", Level.BASIC);
        userDao.add(user);

        // when
        User actual = userDao.findById(user.getId());

        // then
        assertThat(actual).isEqualTo(user);
    }
}
