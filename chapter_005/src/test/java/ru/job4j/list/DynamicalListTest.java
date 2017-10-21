package ru.job4j.list;


import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test.
 */
public class DynamicalListTest {

    /**
     * Add, get.
     */
    @Test
    public void whenNewDynamicalListWithArrayThenGetIt() {

        DynamicalList<String> strings = new DynamicalList<>(2);
        for (int i = 0; i < 25; i++) {
            strings.add("String-" + i);
        }

        String result = strings.get(24);
        String expected = "String-24";
        assertThat(result, is(expected));
    }

    /**
     * Iterable<E>.
     */
    @Test
    public void whenNewDynamicalListIterableThenIterateIt() {

        DynamicalList<String> strings = new DynamicalList<>(2);
        for (int i = 9; i < 25; i++) {
            strings.add("String-" + i);
        }

        Iterator it = strings.iterator();

        while (it.hasNext()) {
            if (it.next().toString().length() > 8) {
                it.remove();
            }
        }

        String result = strings.get(0);
        String expected = "String-9";
        assertThat(result, is(expected));
    }


}