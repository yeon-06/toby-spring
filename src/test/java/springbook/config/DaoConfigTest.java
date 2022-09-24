package springbook.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbc.DaoConfig;
import springbook.jdbc.UserDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
class DaoConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(value = "bean 등록 확인")
    @Test
    void bean() {
        // given & when & then
        assertDoesNotThrow(() -> applicationContext.getBean(UserDao.class));
    }

    @DisplayName(value = "bean으로 등록된 객체는 동일하다")
    @Test
    public void equalsBean() {
        // given
        UserDao userDao1 = applicationContext.getBean(UserDao.class);
        UserDao userDao2 = applicationContext.getBean(UserDao.class);

        // when & then
        assertThat(userDao1).isEqualTo(userDao2);
    }

    @DisplayName(value = "new로 생성한 객체는 동일하지 않다")
    @Test
    public void equalsNew() {
        // given
        DaoConfig daoConfig = new DaoConfig();
        UserDao userDao1 = daoConfig.userDao();
        UserDao userDao2 = daoConfig.userDao();

        // when & then
        assertThat(userDao1).isNotEqualTo(userDao2);
    }
}
