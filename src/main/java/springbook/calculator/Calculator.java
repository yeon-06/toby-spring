package springbook.calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    private final FileReader fileReader;

    public Calculator(final String filePath) {
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int sum() {
        try (BufferedReader br = new BufferedReader(fileReader)) {
            return br.lines()
                    .mapToInt(Integer::parseInt)
                    .sum();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
