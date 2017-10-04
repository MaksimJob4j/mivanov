package ru.job4j.convertlist;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * .
 */
public class ConvertListTest {

    /**
     * .
     */
    @Test
    public void whenSetListOfArraysThenList() {
        ConvertList convertList = new ConvertList();
        List<int[]> list = new ArrayList<>();

        list.add(new int[]{1, 2, 3});
        list.add(new int[]{4, 5, 6, 7});
        List<Integer> result = convertList.convert(list);
        List<Integer> expected = new ArrayList<>(
                Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7})
        );

        assertThat(result, is(expected));
    }

    /**
     * .
     */
    @Test
    public void whenSetArrayThenList() {
        ConvertList convertList = new ConvertList();
        int[][] ints = {{1, 2, 3, 4}, {5}};
        List<Integer> result = convertList.toList(ints);
        List<Integer> expected = new ArrayList<>(
                Arrays.asList(new Integer[]{1, 2, 3, 4, 5})

        );

        assertThat(result, is(expected));
    }

    /**
     * .
     */
    @Test
    public void whenSetListThenArray() {
        ConvertList convertList = new ConvertList();
        List<Integer> list = new ArrayList<>(
                Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
        );

        int[][] result = convertList.toArray(list, 3);
        int[][] expected = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 0}};

        assertThat(result, is(expected));
    }

}
