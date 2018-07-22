package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ковертер списка итераторов в итератор.
 */
public class Converter {
    /**
     * Итератор итераторов.
     * @param it итератор итераторов.
     * @return итератор.
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {

            private Iterator<Integer> currentIt = it.hasNext() ? it.next() : null;

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (currentIt != null) {

                    while (!currentIt.hasNext() && it.hasNext()) {
                        currentIt = it.next();
                    }

                    if (currentIt.hasNext()) {
                        result = true;
                    }
                }
                return result;
            }

            @Override
            public Integer next() {
                Integer result;
                if (hasNext()) {
                    result = currentIt.next();
                } else {
                    throw new NoSuchElementException("Выход за границы последовательности");
                }

                return result;
            }
        };
    }
}
