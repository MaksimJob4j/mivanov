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
    public E get(int index) {
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
    public void delete(int i) {
        if (i > -1 && i < index) {
            if (index - i > 1) {
                System.arraycopy(container, i + 1, container, i, index - i);
            }
            index--;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * delete.
     * @param i i.
     */
    public boolean delete(E e) {
        boolean result = false;
        for (int i = 0; i < index; i++) {
            if (container[i].equals(e)) {
                delete(i);
                result = true;
            }
        }
        return result;
    }

    public boolean contains(E element) {
        for (int i = 0; i < index; i++) {
            if (((E) container[i]).equals(element)) {
                return true;
            }
        }
        return false;
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
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < index; i++) {

            if (container[i] != null) {
                sb.append(container[i]);
                sb.append(", ");
            }
        }
        try {
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));
        } catch (Exception e) { }
        sb.append("]");
        return sb.toString();
    }
}
