package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test.
 */
public class ArraysMergeTest {
    /**
     * Test.
     */
    @Test
    public void whenTwoSortedArraysIncreaseThenMerge() {
        ArraysMerge arraysMerge = new ArraysMerge();
        int[]   first = {1, 4, 5, 8},
                second = {1, 4, 4, 4, 10},
                expected = {1, 1, 4, 4, 4, 4, 5, 8, 10},
                result = arraysMerge.merge(first, second);
        assertThat(result, is(expected));
    }
    /**
     * Test.
     */
    @Test
    public void whenTwoSortedArraysNoIncreaseThenMerge() {
        ArraysMerge arraysMerge = new ArraysMerge();
        int[]   first = {8, 2, -1, -8},
                second = {10, 5, -4},
                expected = {10, 8, 5, 2, -1, -4, -8},
                result = arraysMerge.merge(first, second);
        assertThat(result, is(expected));
    }
    /**
     * Test.
     */
    @Test
    public void whenSortedArrayAndArrayOfEqualsElementsThenMerge() {
        ArraysMerge arraysMerge = new ArraysMerge();
        int[]   first = {4, 4},
                second = {1, 4, 4, 4, 10},
                expected = {1, 4, 4, 4, 4, 4, 10},
                result = arraysMerge.merge(first, second);
        assertThat(result, is(expected));
    }
}
