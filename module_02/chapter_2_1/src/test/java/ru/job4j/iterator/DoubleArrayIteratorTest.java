package ru.job4j.iterator;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * DoubleArrayIteratorTest.
 */
public class DoubleArrayIteratorTest {

    /**
     * Тест hasNext true.
     */
    @Test
    public void whenHasNextCallThenShouldReturnTrue() {
        DoubleArrayIterator it = new DoubleArrayIterator(
                new int[][]
                                {{1, 2, 3},
                                {11},
                                {21, 22}});
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    /**
     * Тест hasNext false.
     */
    @Test
    public void whenHasNextCallThenShouldReturnFalse() {
        DoubleArrayIterator it = new DoubleArrayIterator(
                new int[][]
                                {{1, 2, 3},
                                {11},
                                {21, 22}});

        for (int i = 0; i < 6; i++) {
            it.next();
        }
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }

    /**
     * Тест next.
     */
    @Test
    public void whenGetNextCallThenShouldPointedForward() {
        DoubleArrayIterator it = new DoubleArrayIterator(
                new int[][]
                                {{1, 2, 3},
                                {11},
                                {21, 22}});
        it.next();
        it.next();
        it.next();
        it.next();
        int result = (int) it.next();
        assertThat(result, is(21));
    }

}