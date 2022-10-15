package springbook.jdbctemplate.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import springbook.jdbctemplate.domain.User;

@Component
public class StubUserDao implements UserDao {

    private List<User> users = new ArrayList<>();

    @Override
    public void save(final User user) {
        users.add(user);
    }

    @Override
    public User findById(final String id) {
        List<User> matchUsers = users.stream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());

        validateSize(matchUsers);
        return matchUsers.get(0);
    }

    private void validateSize(final List<User> users) {
        int size = users.size();
        if (size == 0) {
            throw new EmptyResultDataAccessException(1);
        }
        if (size > 1) {
            throw new IncorrectResultSizeDataAccessException(1, size);
        }
    }

    @Override
    public void update(final User user) {
        User foundUser = findById(user.getId());
        users.remove(foundUser);
        users.add(user);
    }

    @Override
    public void deleteAll() {
        users = new ArrayList<>();
    }
}
