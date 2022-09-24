package springbook.calculator;

public interface LineCallback<T> {

    T executeWithLine(String line, T value);
}
