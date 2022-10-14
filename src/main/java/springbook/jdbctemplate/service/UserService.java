package springbook.jdbctemplate.service;

import java.util.List;
import springbook.jdbctemplate.User;

public interface UserService {

    void upgradeLevels(List<User> users);
    void upgradeLevel(final User user);
}
