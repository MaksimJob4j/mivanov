package ru.job4j.generics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleArray<T>.
 * @param <T> Класс.
 */
public class SimpleArray<T> implements Iterable<T> {
    /**
     * Массив.
     */
    private T[] array;
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
                array = (T[]) new Object[]{t};
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

    /**
     * update.
     * @param t T.
     */
    public void update(T t) {
        for (int i = 0; i < index; i++) {
            if (array[i].equals(t)) {
                array[i] = t;
            }
        }
    }

    @Override
    public String toString() {

        return ((T[]) array).toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int i = 0;
            private boolean isNextCall;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public T next() {
                T returnT;
                if (i < index) {
                    returnT = (T) array[i++];
                    isNextCall = true;
                } else {
                    throw new NoSuchElementException();
                }
                return returnT;
            }

            @Override
            public void remove() {
                if (isNextCall) {
                    delete(--i);
                    isNextCall = false;
                } else {
                    throw new IllegalStateException();
                }
            }
        };
    }
}
