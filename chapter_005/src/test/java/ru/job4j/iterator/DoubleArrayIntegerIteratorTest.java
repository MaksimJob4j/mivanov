package ru.job4j.iterator;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * DoubleArrayIntegerIteratorTest.
 */
public class DoubleArrayIntegerIteratorTest {

    /**
     * Тест hasNext true.
     */
    @Test
    public void whenHasNextCallThenShouldReturnTrue() {
        DoubleArrayIntegerIterator it = new DoubleArrayIntegerIterator(
                new Integer[][]
                                {{1, 2, 3},
                                {},
                                {21},
                                {31, 32}});
        it.next();
        it.next();
        it.next();
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    /**
     * Тест hasNext false.
     */
    @Test
    public void whenHasNextCallThenShouldReturnFalse() {
        DoubleArrayIntegerIterator it = new DoubleArrayIntegerIterator(
                new Integer[][]
                                {{1, 2, 3},
                                {},
                                {21},
                                {31, 32}});

        for (int i = 0; i < 7; i++) {
            it.next();
        }
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }

    /**
     * Тест next null.
     */
    @Test
    public void whenGetNextCallToEmptyThenShouldPointedToEmpty() {
        DoubleArrayIntegerIterator it = new DoubleArrayIntegerIterator(
                new Integer[][]
                                {{1, 2, 3},
                                {},
                                {21},
                                {31, 32}});
        it.next();
        it.next();
        it.next();
        Integer result = (Integer) it.next();
        Integer expected = null;
        assertThat(result, is(expected));
    }


}