package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * MaxTest.
 */
public class MaxTest {
    /**
     * Test.
     */
    @Test
    public void whenCompareOneAndTowThenTwo() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        int expected = 2;
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenCompareTwoAndOneThenTwo() {
        Max maximum = new Max();
        int result = maximum.max(2, 1);
        int expected = 2;
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenCompareTwoAndTwoThenTwo() {
        Max maximum = new Max();
        int result = maximum.max(2, 2);
        int expected = 2;
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenCompareOneAndTwoAndThreeThenThree() {
        Max maximum = new Max();
        int result = maximum.max(1, 2, 3);
        int expected = 3;
        assertThat(result, is(expected));
    }

}
