package ru.job4j.array;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Test.
 */
public class RotateArrayTest {
    /**
     * Test.
     */
    @Test
    public void whenSetThreeRowTreeColArrayThenRotateIt() {
        RotateArray rotateArray = new RotateArray();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                expected = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}},
                result = rotateArray.rotate(array);
        assertThat(expected, is(result));

    }
    /**
     * Test.
     */
    @Test
    public void whenSetFourRowFourColArrayThenRotateIt() {
        RotateArray rotateArray = new RotateArray();
        int[][] array = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}},
                expected = {{13, 9, 5, 1}, {14, 10, 6, 2}, {15, 11, 7, 3}, {16, 12, 8, 4}},
                result = rotateArray.rotate(array);
        assertThat(expected, is(result));

    }
}
