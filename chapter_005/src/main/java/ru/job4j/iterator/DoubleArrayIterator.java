package ru.job4j.iterator;

import java.util.Iterator;

/**
 * Итератор двумерного массива int.
 */
public class DoubleArrayIterator implements Iterator {
    /**
     * Массив.
     */
    private final int[][] doubleArray;
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
     * @param doubleArray int[][].
     */
    public DoubleArrayIterator(final int[][] doubleArray) {
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
        int result = doubleArray[iIndex][jIndex];
        if (doubleArray[iIndex].length - jIndex > 1) {
            jIndex++;
        } else {
            iIndex++;
            jIndex = 0;
        }
        return result;
    }
}
