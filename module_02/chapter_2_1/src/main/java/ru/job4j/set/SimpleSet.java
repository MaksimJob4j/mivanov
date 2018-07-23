package ru.job4j.set;

import ru.job4j.list.DynamicalList;

import java.util.Iterator;
import java.util.List;

/**
 * SimpleSet.
 * @param <E> тип.
 */
public class SimpleSet<E> implements Iterable<E> {
    /**
     *
     *
     */
    private DynamicalList<E> items = new DynamicalList<>(10);
    /**
     * iterator.
     */
    private Iterator<E> iterator = items.iterator();

    public SimpleSet() {
    }

    public SimpleSet(List<E> list) {
        for (E e: list) {
            items.add(e);
        }
    }

    /**
     * add.
     * @param e элемент.
     */
    void add(E e) {
        if (e != null && !contains(e)) {
            items.add(e);
        }

    }

    /**
     * Проверка на содержание элемента в множестве.
     * @param e Элемент.
     * @return true если элемент содержится во множестве.
     */
    public boolean contains(E e) {
        Boolean result = false;
        Iterator<E> it = items.iterator();
        while (!result && it.hasNext()) {
            if (it.next().equals(e)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        Iterator it = items.iterator();
        StringBuilder sb = new StringBuilder();
        if (it.hasNext()) {
            sb.append("[");
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(", ");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));
            sb.append("]");
        }
        return sb.toString();
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next();
            }

        };
    }
}
