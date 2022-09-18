package springbook.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    private static final Set<JUnitTest> jUnitTests = new HashSet<>();

    @Test
    void test1() {
        // given & when & then
        assertThat(jUnitTests).doesNotHaveSameClassAs(this);
        jUnitTests.add(this);
    }

    @Test
    void test2() {
        // given & when & then
        assertThat(jUnitTests).doesNotHaveSameClassAs(this);
        jUnitTests.add(this);
    }

    @Test
    void test3() {
        // given & when & then
        assertThat(jUnitTests).doesNotHaveSameClassAs(this);
        jUnitTests.add(this);
    }
}
