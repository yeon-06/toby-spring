package springbook.proxy;

public class LogyeonWord implements Word {

    private static final String NAME = "yeonlog";

    @Override
    public String sayHello() {
        return String.format("Hello %s!", NAME);
    }

    @Override
    public String sayThanks() {
        return String.format("Thank you %s!", NAME);
    }
}
