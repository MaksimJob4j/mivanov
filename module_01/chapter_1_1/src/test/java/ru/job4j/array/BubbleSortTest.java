package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class BubbleSortTest {
    /**
     * Test.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[]   array = {7, 15, 8, 5, 7, 44, 8, 10, 55, 0},
                expected = {0, 5, 7, 7, 8, 8, 10, 15, 44, 55},
                result = bubbleSort.sort(array);
        assertThat(result, is(expected));

    }
}
