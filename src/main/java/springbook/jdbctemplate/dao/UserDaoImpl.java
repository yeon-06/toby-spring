package springbook.jdbctemplate.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(final User user) {
        String sql = "insert into users(id, name, password, level) values(?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue());
    }

    @Override
    public User findById(final String id) {
        String sql = "select id, name, password, level from users where id = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"),
                Level.of(rs.getInt("level")));

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public void update(final User user) {
        String sql = "update users set name = ?, password = ?, level = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getLevel().getValue(), user.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }
}
