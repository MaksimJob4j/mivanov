package ru.job4j.iterator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Итератор возвращающий только простые числа.
 */
public class PrimeItTest {

    /**
     * hasNext Test True.
     */
    @Test
    public void whenHasNextCallThenTrue() {
        PrimeIt primeIt = new PrimeIt(new int[] {1, 2});
        boolean result = primeIt.hasNext();
        assertThat(result, is(true));
    }

    /**
     * hasNext Test False.
     */
    @Test
    public void whenHasNextCallThenFalse() {
        PrimeIt primeIt = new PrimeIt(new int[] {1, 4, 6});
        boolean result = primeIt.hasNext();
        assertThat(result, is(false));
    }

    /**
     * next Test Volume.
     */
    @Test
    public void whenNextCallThenVolume() {
        PrimeIt primeIt = new PrimeIt(new int[] {1, 2});
        int result = (int) primeIt.next();
        assertThat(result, is(2));
    }

    /**
     * next Test Exception.
     */
    @Test
    public void whenNextCallThenException() {
        PrimeIt primeIt = new PrimeIt(new int[] {1, 2, 4});
        primeIt.next();
        String result = "";
        try {
            primeIt.next();
        } catch (Exception e) {
            result = e.getMessage();
        }
        assertThat(result, is("Нет простых чисел для возврата"));
    }

}