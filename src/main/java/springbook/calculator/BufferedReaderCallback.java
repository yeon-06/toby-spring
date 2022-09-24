package springbook.calculator;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {

    int calculateWithReader(BufferedReader br) throws IOException;
}
