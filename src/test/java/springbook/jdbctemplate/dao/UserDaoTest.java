package springbook.jdbctemplate.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.jdbctemplate.DataSourceConfig;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.domain.User;
import springbook.jdbctemplate.supporter.SqlFinder;
import springbook.jdbctemplate.supporter.SqlReaderImpl;
import springbook.jdbctemplate.supporter.SqlRegistryImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class, UserDaoImpl.class, SqlFinder.class, SqlRegistryImpl.class,
        SqlReaderImpl.class})
class UserDaoTest {

    @Autowired
    private UserDaoImpl userDao;

    @BeforeEach
    void delete() {
        userDao.deleteAll();
    }

    @DisplayName(value = "사용자 조회하기")
    @Test
    void findById() {
        // given
        User user = new User("id", "name", "1234", Level.SILVER);
        userDao.save(user);

        // when
        User actual = userDao.findById(user.getId());

        // then
        assertThat(actual).isEqualTo(user);
    }

    @DisplayName(value = "사용자 레벨 업그레이드")
    @Test
    void update() {
        // given
        String id = "yeonlog";
        User user = new User(id, "name", "1234", Level.SILVER);
        userDao.save(user);

        user.upgradeLevel();

        // when
        userDao.update(user);

        // then
        User actual = userDao.findById(id);
        assertThat(actual.getLevel()).isEqualTo(Level.SILVER.nextLevel());
    }
}
