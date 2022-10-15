package springbook.proxy.domain;

public class ProxyWord implements Word {

    private final Word word;

    public ProxyWord(final Word word) {
        this.word = word;
    }

    @Override
    public String sayHello() {
        return toUpperCase(word.sayHello());
    }

    @Override
    public String sayThanks() {
        return toUpperCase(word.sayThanks());
    }

    private String toUpperCase(final String string) {
        return string.toUpperCase();
    }
}
