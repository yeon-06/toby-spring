package springbook.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WordFactory implements FactoryBean<Word> {

    @Override
    public Word getObject() throws Exception {
        return new ProxyWord(new YeonlogWord());
    }

    @Override
    public Class<?> getObjectType() {
        return YeonlogWord.class;
    }

    @Override
    public boolean isSingleton() {
        return false; // 이 팩토리 빈은 요청할 때마다 새로 생성하니 false로 해둔다
    }
}
