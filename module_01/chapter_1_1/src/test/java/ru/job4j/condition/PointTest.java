package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * PointTest.
 */
public class PointTest {
    /**
     * Test.
     */
    @Test
    public void whenSetPointOneThreeThenPointOnLineOneAndTwo() {
        Point point = new Point(1, 3);
        boolean result = point.isPointOnLine(1, 2);
        boolean expected = true;
        assertThat(result, is(expected));
    }

}
