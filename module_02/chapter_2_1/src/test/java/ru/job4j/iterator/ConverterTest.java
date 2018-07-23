package ru.job4j.iterator;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 *  Ковертер списка итераторов в итератор.
 */
public class ConverterTest {

    /**
     * Вызов элемента из списка.
     */
    @Test
    public void whenItHasTwoInnerIt() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();
        Iterator<Integer> convert = new Converter().convert(it);
        convert.next();
        int result = convert.next();
        assertThat(result, is(2));
    }

    /**
     * Вызов next() за границами списка.
     */
    @Test
    public void whenHasNoElementsThenException() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();
        Iterator<Integer> convert = new Converter().convert(it);
        convert.next();
        convert.next();
        String result = "";
        try {
            convert.next();
        } catch (Exception e) {
            result = e.getMessage();
        }
        assertThat(result, is("Выход за границы последовательности"));
    }

    /**
     * Нахождение элемента после пустого списка.
     */
    @Test
    public void whenNextCallThenGetVolume() {
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);

        List<Integer> list2 = new ArrayList<Integer>();
        List<Integer> list3 = new ArrayList<Integer>();
        list3.add(4);
        Iterator<Iterator<Integer>> it = Arrays.asList(
                list1.iterator(),
                list2.iterator(),
                list3.iterator()
        ).iterator();
        Iterator<Integer> convert = new Converter().convert(it);
        convert.next();
        convert.next();
        int result = convert.next();
        assertThat(result, is(4));
    }

}