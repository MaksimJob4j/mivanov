package ru.job4j.iterator;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Итератор, возвращающий только четные значения.
 */
public class EvenItTest {

    /**
     * hasNext Test True.
     */
    @Test
    public void whenHasNextCallThenTrue() {
        EvenIt evenIt = new EvenIt(new int[] {2, 1, 1});
        boolean result = evenIt.hasNext();
        assertThat(result, is(true));
    }

    /**
     * hasNext Test False.
     */
    @Test
    public void whenHasNextCallThenFalse() {
        EvenIt evenIt = new EvenIt(new int[] {1, 3});
        boolean result = evenIt.hasNext();
        assertThat(result, is(false));
    }

    /**
     * next Test Volume.
     */
    @Test
    public void whenNextCallThenVolume() {
        EvenIt evenIt = new EvenIt(new int[] {2, 4, 3});
        int result = (int) evenIt.next();
        assertThat(result, is(2));
    }

    /**
     * next Test Exception.
     */
    @Test
    public void whenNextCallThenException() {
        EvenIt evenIt = new EvenIt(new int[] {1, 7, 3});
        String result = "";
        try {
            evenIt.next();
        } catch (Exception e) {
            result = e.getMessage();
        }
        assertThat(result, is("Нет четных элементов для возврата"));
    }

}