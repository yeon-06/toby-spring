package springbook.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDbTest {

    private EmbeddedDatabase db;
    private NamedParameterJdbcTemplate template;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("init.sql")
                .build();

        template = new NamedParameterJdbcTemplate(db);
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void initData() {
        // given
        String sql = "select count(*) from sqlmap";

        // when
        Integer count = template.queryForObject(sql, Map.of(), Integer.class);

        // then
        assertThat(count).isEqualTo(2);
    }
}
