package ru.job4j.iterator;

import java.util.Iterator;

/**
 * Итератор двумерного массива Integer (могут присутствовать null значения).
 */
public class DoubleArrayIntegerIterator implements Iterator {
    /**
     * Массив.
     */
    private final Integer[][] doubleArray;
    /**
     * indexi.
     */
    private int iIndex = 0;
    /**
     * indexj.
     */
    private int jIndex = 0;


    /**
     * Конструктор.
     * @param doubleArray Integer[][].
     */
    public DoubleArrayIntegerIterator(final Integer[][] doubleArray) {
        this.doubleArray = doubleArray;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        if (iIndex < doubleArray.length) {
            result = true;
        }
        return result;
    }

    @Override
    public Object next() {
        Integer result;
        if (doubleArray[iIndex].length == 0) {
            result = null;
            iIndex++;
            jIndex = 0;
        } else {
            result = doubleArray[iIndex][jIndex];
            if (doubleArray[iIndex].length - jIndex > 1) {
                jIndex++;
            } else {
                iIndex++;
                jIndex = 0;
            }
        }
        return result;
    }
}
