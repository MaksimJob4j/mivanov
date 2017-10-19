package ru.job4j.generics;

import java.util.Arrays;

/**
 * SimpleArray<T>.
 * @param <T> Класс.
 */
public class SimpleArray<T> {
    /**
     * Массив.
     */
    private Object[] array;
    /**
     * Индекс.
     */
    private int index = 0;

    /**
     * getSize.
     * @return size.
     */
    public int getSize() {
        return index;
    }

    /**
     * Конструктор.
     */
    public SimpleArray() {
    }
    /**
     * Конструктор.
     * @param array массив.
     */
    public SimpleArray(T[] array) {
        this.array = array;
    }


    /**
     * add.
     * @param t T.
     */
    public void add(T t) {
        if (t != null) {
            if (array == null) {
                array = new Object[]{t};
                index++;
            } else if (index < array.length) {
                array[index++] = t;
            } else {
                array = Arrays.copyOf(array, (int) (array.length < 10 ? 10 : array.length * 1.5));
                array[index++] = t;
            }
        }
    }

    /**
     * delete.
     * @param i i.
     */
    public void delete(int i) {
        if (i > -1 && i < index) {
            if (index - i > 1) {
                System.arraycopy(array, i + 1, array, i, index - i);
            }
            index--;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }

    /**
     * delete.
     * @param t T.
     */
    public void delete(T t) {
        for (int i = 0; i < index; i++) {
            if (array[i].equals(t)) {
                delete(i);
                i = index;
            }
        }
    }

    /**
     * get.
     * @param i i.
     * @return T.
     */
    public T get(int i) {
        T result;
        if (i > -1 && i < index) {
            result = (T) array[i];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        return result;

    }

    @Override
    public String toString() {

        return ((T[]) array).toString();
    }
}
