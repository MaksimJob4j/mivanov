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
     * row.
     */
    private int row = 0;
    /**
     * column.
     */
    private int column = 0;

    /**
     * Конструктор.
     * @param doubleArray int[][].
     */
    public DoubleArrayIterator(final int[][] doubleArray) {
        this.doubleArray = doubleArray;
    }

    @Override
    public boolean hasNext() {
        return row < doubleArray.length;
    }

    @Override
    public Object next() {
        int result = doubleArray[row][column];
        if (doubleArray[row].length - column > 1) {
            column++;
        } else {
            row++;
            column = 0;
        }
        return result;
    }
}
