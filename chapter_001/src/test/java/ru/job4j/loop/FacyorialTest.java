package ru.job4j.loop;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * FacyorialTest.
 */
public class FacyorialTest {
    /**
     * Test.
     */
    @Test
    public void whenSetNumberThenFactorial() {
        Factorial factorial  = new Factorial();
        int result = factorial.calc(5);
        int expected = 120;
        assertThat(result, is(expected));
    }
}
