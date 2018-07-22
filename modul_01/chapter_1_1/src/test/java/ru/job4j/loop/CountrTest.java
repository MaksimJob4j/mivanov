package ru.job4j.loop;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * CounterTest.
 */
public class CountrTest {
    /**
     * Test.
     */
    @Test
    public void whenSetTwoAndFiveThenSix() {
        Counter counter  = new Counter();
        int result = counter.add(2, 5);
        int expected = 6;
        assertThat(result, is(expected));
    }
}
