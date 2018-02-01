package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Потокобезопасный LinkedList.
 * @param <E> generic.
 * */
@ThreadSafe
public class SynchrLinkedList<E> implements Iterable<E> {
    /**
     * getFirst.
     */
    private Node getFirst() {
        return first;
    }

    /**
     * getLast.
     * @return E.
     */
    private Node getLast() {
        return last;
    }

    /**
     * Node.
     */
    private class Node {
        /**
         * item.
         */
        private E item;
        /**
         * prev.
         */
        private Node prev;
        /**
         * next.
         */
        private Node next;

        /**
         * setNext.
         * @param next Node.
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * node.
         * @param item E.
         */
        Node(E item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{item=" + item + '}';
        }

    }

    /**
     * index.
     */
    @GuardedBy("this")
    private int index = 0;

    /**
     * header.
     */
    @GuardedBy("this")
    private Node header;

    /**
     * Конструктор.
     */
    public SynchrLinkedList() {
        header = new Node(null);
    }

    /**
     * first.
     */
    @GuardedBy("this")
    private Node first;
    /**
     * last.
     */
    @GuardedBy("this")
    private Node last;

    /**
     * getItem.
     * @param node node.
     * @return E.
     */
    private E getItem(Node node) {
        return node.item;
    }

    /**
     * add.
     * @param item E.
     */
    public synchronized void add(E item) {
        Node node = new Node(item);

        if (index++ == 0) {
            first = node;
            header.next = first;
            node.prev = header;
            last = node;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
        }
    }

    /**
     * get.
     * @param index int.
     * @return Node.
     */
    private synchronized Node getNode(int index) {
        if (index > -1 && index < this.index) {
            Node node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * get.
     * @param index int.
     * @return E.
     * @throws IndexOutOfBoundsException .
     */
    public synchronized E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).item;
    }

    /**
     * remove.
     * @param node Node.
     */
    private boolean remove(Node node) {
        boolean result = false;
        if (node != null) {
            synchronized (header) {
                if (node.next != null) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    first = header.next;
                } else {
                    last = node.prev;
                    last.next = null;
                }
                if (--index == 0) {
                    first = null;
                    last = null;
                }
                result = true;
            }
        }
        return result;
    }

    /**
     * remove.
     * @param index int.
     */
    public synchronized void remove(int index) {
        remove(getNode(index));
    }

    public synchronized boolean remove(E element) {
        return remove(findNode(element));
    }

    public synchronized boolean contain(E element) {
        return findNode(element) != null;
    }

    private Node findNode(E element) {
        for (int i = 0; i < index; i++) {
            if (getNode(i).item.equals(element)) {
                return getNode(i);
            }
        }
        return null;
    }

    private synchronized Object[] makeArray() {

        Object[] returnArray = new Object[index];
        int i = 0;
        Node node = header;

        while ((node = node.next) != null) {
            returnArray[i++] = node.item;
        }

        return returnArray;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int i = 0;
            final Object[] containerIt = makeArray();
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
        final Object[] stringContainer = makeArray();
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
