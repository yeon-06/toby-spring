package springbook.jdbctemplate.dao;

import springbook.jdbctemplate.domain.User;

public interface UserDao {

    void save(User user);

    User findById(String id);

    void update(User user);

    void deleteAll();
}
