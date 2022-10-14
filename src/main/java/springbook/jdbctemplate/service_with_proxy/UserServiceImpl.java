package springbook.jdbctemplate.service_with_proxy;

import java.util.List;
import org.springframework.stereotype.Component;
import springbook.jdbctemplate.User;
import springbook.jdbctemplate.dao.UserDao;

@Component
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels(final List<User> users) {
        for (User user : users) {
            upgradeLevel(user);
        }
    }

    public void upgradeLevel(final User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
