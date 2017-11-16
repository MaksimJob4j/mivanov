package ru.job4j.threads;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Потокобезопасный ArrayList.
 * @param <E> generic.
 */
public class SynchrArrayList<E> implements Iterable<E> {
    /**
     * Container.
     */
    private Object[] container;
    /**
     * index.
     */
    private volatile int index = 0;

    /**
     * Конструктор.
     * @param n int.
     */
    public SynchrArrayList(int n) {
        this.container = new Object[n];
    }

    /**
     * add.
     * @param value E.
     */
    public synchronized void add(E value) {
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
    public synchronized E get(int index) {
        E result;
        if (index > -1 && index < this.index) {
            result = (E) container[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        return result;
    }

    /**
     * remove.
     * @param index index.
     */
    public synchronized void remove(int index) {
        if (index > -1 && index < this.index) {
            if (this.index - index > 1) {
                System.arraycopy(container, index + 1, container, index, this.index - index);
            }
            this.index--;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * remove.
     * @param e e.
     * @return true если элемент удален.
     */
    public synchronized boolean remove(E e) {
        boolean result = false;
        for (int i = 0; i < index; i++) {
            if (container[i].equals(e)) {
                remove(i);
                result = true;
            }
        }
        return result;
    }

    public synchronized boolean contains(E element) {
        for (int i = 0; i < index; i++) {
            if (((E) container[i]).equals(element)) {
                return true;
            }
        }
        return false;
    }

    private synchronized Object[] makeContainer() {
        return Arrays.copyOfRange(container, 0, index);
    }



    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int i = 0;
            final Object[] containerIt = makeContainer();
            int indexIt = containerIt.length;

            @Override
            public boolean hasNext() {
                return i < indexIt;
            }

            @Override
            public E next() {
                E result;
                synchronized (containerIt) {
                    if (i < indexIt) {
                        result = (E) containerIt[i++];
                    } else {
                        throw new NoSuchElementException();
                    }
                }
                return result;
            }

        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        final Object[] stringContainer = makeContainer();
        for (int i = 0; i < stringContainer.length; i++) {
            sb.append(stringContainer[i]);
            sb.append(", ");
        }
        try {
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));
        } catch (Exception e) { }
        sb.append("]");
        return sb.toString();
    }
}