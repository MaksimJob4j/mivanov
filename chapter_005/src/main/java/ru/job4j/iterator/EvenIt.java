package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор принимает список произвольных чисел возвращает только четные значения.
 */
public class EvenIt implements Iterator {
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
    public EvenIt(int[] array) {
        this.array = array;
    }


    @Override
    public boolean hasNext() {
        return indexOfNextEvenElement() != -1;
    }

    @Override
    public Object next() {
        int result;
        if (indexOfNextEvenElement() != -1) {
            index = indexOfNextEvenElement();
            result = array[index];
        } else {
            throw new NoSuchElementException("Нет четных элементов для возврата");
        }
        return result;
    }

    /**
     * Поиск следующего элемента с четным значением.
     * @return индекс следующего четного элемента или "-1" в случае его отсутствия.
     */
    private int indexOfNextEvenElement() {
        int returnIndex = index;
        do {
            returnIndex++;
        } while (returnIndex < array.length && array[returnIndex] % 2 != 0);
        return returnIndex == array.length ? -1 : returnIndex;
    }

}
