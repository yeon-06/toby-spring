package springbook.jdbctemplate.supporter;

import org.springframework.beans.factory.InitializingBean;

public interface SqlService extends InitializingBean {

    String getSql(String key);
}
