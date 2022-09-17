package springbook.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.UserDao;

class DaoConfigTest {

    @DisplayName(value = "bean 등록 확인")
    @Test
    void name() {
        // given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoConfig.class);

        // when & then
        assertDoesNotThrow(() -> applicationContext.getBean(UserDao.class));
    }
}
