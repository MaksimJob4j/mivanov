package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор возвращающий только простые числа.
 */
public class PrimeIt implements Iterator {
    /**
     * Массив.
     */
    private int[] array;
    /**
     * index.
     */
    private int index = -1;

    /**
     * Конструктор.
     * @param array int[].
     */
    public PrimeIt(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return indexOfNextPrimeElement() != -1;
    }

    @Override
    public Object next() {
        int result;
        if (indexOfNextPrimeElement() != -1) {
            index = indexOfNextPrimeElement();
            result = array[index];
        } else {
            throw new NoSuchElementException("Нет простых чисел для возврата");
        }
        return result;
    }

    /**
     * Поиск следующего простого числа.
     * @return индекс следующего простого числа или "-1" в случае его отсутствия.
     */
    private int indexOfNextPrimeElement() {
        int returnIndex = index;
        do {
            returnIndex++;
        } while (returnIndex < array.length && !isPrime(array[returnIndex]));
        return returnIndex == array.length ? -1 : returnIndex;
    }

    /**
     * Проверка числа на простоту.
     * @param i число.
     * @return true если простое или false.
     */
    private boolean isPrime(int i) {
        boolean result = true;
        if (i == 1) {
            result = false;
        }
        int j = 2;
        while (result && j <= Math.sqrt(i)) {
            if (i % j == 0) {
                result = false;
            }
            j++;
        }
        return result;

    }


}
