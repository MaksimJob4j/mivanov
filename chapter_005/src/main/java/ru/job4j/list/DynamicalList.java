package ru.job4j.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * DynamicalList.
 * @param <E> E.
 */
public class DynamicalList<E> implements Iterable<E> {
    /**
     * Container.
     */
    private Object[] container;
    /**
     * index.
     */
    private int index = 0;

    /**
     * Конструктор.
     * @param n int.
     */
    public DynamicalList(int n) {
        this.container = new Object[n];
    }

    /**
     * add.
     * @param value E.
     */
    public void add(E value) {
        if (index >= container.length) {
            container = Arrays.copyOf(container, (int) (container.length < 10 ? 10 : container.length * 1.5));
        }
        container[index++] = value;
    }

    /**
     * get.
     * @param index int.
     * @return E.
     */
    E get(int index) {
        E result;
        if (index > -1 && index < this.index) {
            result = (E) container[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        return result;
    }

    /**
     * delete.
     * @param i i.
     */
    void delete(int i) {
        if (i > -1 && i < index) {
            if (index - i > 1) {
                System.arraycopy(container, i + 1, container, i, index - i);
            }
            index--;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int i = 0;
            private boolean isNextCall;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public E next() {
                E returnT;
                if (i < index) {
                    returnT = (E) container[i++];
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

    @Override
    public String toString() {
        return Arrays.toString(container);
    }
}
