package ru.job4j.iterator;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Итератор, возвращающий только четные значения.
 */
public class EvenIteratorTest {

    /**
     * hasNext Test True.
     */
    @Test
    public void whenHasNextCallThenTrue() {
        EvenIterator evenIterator = new EvenIterator(new int[] {1, 2, 3});
        boolean result = evenIterator.hasNext();
        assertThat(result, is(true));
    }

    /**
     * hasNext Test False.
     */
    @Test
    public void whenHasNextCallThenFalse() {
        EvenIterator evenIterator = new EvenIterator(new int[] {1, 3});
        boolean result = evenIterator.hasNext();
        assertThat(result, is(false));
    }

    /**
     * next Test Volume.
     */
    @Test
    public void whenNextCallThenVolume() {
        EvenIterator evenIterator = new EvenIterator(new int[] {1, 2, 3});
        int result = (int) evenIterator.next();
        assertThat(result, is(2));
    }

    /**
     * next Test Exception.
     */
    @Test
    public void whenNextCallThenException() {
        EvenIterator evenIterator = new EvenIterator(new int[] {1, 2, 3});
        evenIterator.next();
        String result = "";
        try {
            evenIterator.next();
        } catch (Exception e) {
            result = e.getMessage();
        }
        assertThat(result, is("Нет четных элементов для возврата"));
    }

}