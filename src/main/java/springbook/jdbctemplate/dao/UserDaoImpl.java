package springbook.jdbctemplate.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import springbook.jdbctemplate.domain.Level;
import springbook.jdbctemplate.domain.User;
import springbook.jdbctemplate.supporter.SqlFinder;

@Repository
public class UserDaoImpl implements UserDao {

    private final SqlFinder sqlFinder;
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final SqlFinder sqlFinder, final DataSource dataSource) {
        this.sqlFinder = sqlFinder;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(final User user) {
        String sql = sqlFinder.get("sql.user.save");
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue());
    }

    @Override
    public User findById(final String id) {
        String sql = sqlFinder.get("sql.user.findById");
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("password"),
                Level.of(rs.getInt("level")));

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public void update(final User user) {
        String sql = sqlFinder.get("sql.user.update");
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getLevel().getValue(), user.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(sqlFinder.get("sql.user.deleteAll"));
    }
}
