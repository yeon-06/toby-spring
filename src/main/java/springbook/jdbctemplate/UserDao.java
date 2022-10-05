package springbook.jdbctemplate;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        String sql = "insert into users(id, name, password, level) values(?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue());
    }

    public User findById(final String id) {
        String sql = "select id, name, password, level from users where id = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"),
                Level.of(rs.getInt("level")));

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void update(final User user) {
        String sql = "update users set name = ?, password = ?, level = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getLevel().getValue(), user.getId());
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }
}
