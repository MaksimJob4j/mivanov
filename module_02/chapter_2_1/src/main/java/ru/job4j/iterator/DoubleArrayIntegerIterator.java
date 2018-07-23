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
     * row.
     */
    private int row = 0;
    /**
     * column.
     */
    private int column = 0;


    /**
     * Конструктор.
     * @param doubleArray Integer[][].
     */
    public DoubleArrayIntegerIterator(final Integer[][] doubleArray) {
        this.doubleArray = doubleArray;
    }

    @Override
    public boolean hasNext() {
        return row < doubleArray.length;
    }

    @Override
    public Object next() {
        Integer result;
        if (doubleArray[row].length == 0) {
            result = null;
            row++;
            column = 0;
        } else {
            result = doubleArray[row][column];
            if (doubleArray[row].length - column > 1) {
                column++;
            } else {
                row++;
                column = 0;
            }
        }
        return result;
    }
}
