package ru.job4j.set;

import ru.job4j.list.MyLinkedList;

import java.util.Iterator;
import java.util.List;

/**
 * Set на LinkedList.
 * @param <E> Тип.
 */
public class SimpleLinkedSet<E> implements Iterable<E> {
    /**
     * MyLinkedList for Set.
     */
    private MyLinkedList<E> set = new MyLinkedList<>();

    public SimpleLinkedSet() {
    }

    public SimpleLinkedSet(List<E> list) {
        for (E e: list) {
            set.add(e);
        }
    }

    /**
     * Iterator.
     * @return iterator.
     */
    public Iterator<E> iterator() {
        return set.iterator();
    }

    /**
     * add.
     * @param e элемент.
     */
    void add(E e) {
        if (e != null && !contains(e)) {
            set.add(e);
        }

    }

    /**
     * Проверка на содержание элемента в множестве.
     * @param e Элемент.
     * @return true если элемент содержится во множестве.
     */
    public boolean contains(E e) {
        Boolean result = false;
        Iterator<E> it = set.iterator();
        while (!result && it.hasNext()) {
            if (it.next().equals(e)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        Iterator it = set.iterator();
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

}
