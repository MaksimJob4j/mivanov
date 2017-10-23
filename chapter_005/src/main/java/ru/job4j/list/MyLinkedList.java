package ru.job4j.list;

import java.util.Iterator;

/**
 * MyLinkedList.
 * @param <E> E.
 */
public class MyLinkedList<E> implements Iterable<E> {

    /**
     * getFirst.
     * @return E.
     */
    public Node<E> getFirst() {
        return first;
    }

    /**
     * getLast.
     * @return E.
     */
    public Node<E> getLast() {
        return last;
    }

    /**
     * Node.
     * @param <E> E.
     */
    class Node<E> {
        /**
         * item.
         */
        private E item;
        /**
         * prev.
         */
        private Node<E> prev;
        /**
         * next.
         */
        private Node<E> next;

        /**
         * setNext.
         * @param next Node.
         */
        public void setNext(Node<E> next) {
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
    private int index = 0;

    /**
     * header.
     */
    private Node<E> header;

    /**
     * Конструктор.
     */
    public MyLinkedList() {
        header = new Node<E>(null);
    }

    /**
     * first.
     */
    private Node<E> first;
    /**
     * last.
     */
    private Node<E> last;

    /**
     * getItem.
     * @param node node.
     * @return E.
     */
    E getItem(Node<E> node) {
        return node.item;
    }

    /**
     * add.
     * @param item E.
     */
    public void add(E item) {
        Node<E> node = new Node<E>(item);

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
    Node<E> getNode(int index) {
        if (index > -1 && index < this.index) {
            Node<E> node = first;
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
    E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).item;
    }

    /**
     * remove.
     * @param node Node.
     */
    void remove(Node node) {
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

    }

    /**
     * remove.
     * @param index int.
     */
    void remove(int index) {
        remove(getNode(index));
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> nodeIt = header;

            @Override
            public boolean hasNext() {
                return nodeIt.next != null;
            }

            @Override
            public E next() {
                nodeIt = nodeIt.next;
                return nodeIt.item;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (header.next != null) {
            sb.append("{");
            Node node = header;

            while (node.next != null) {
                sb.append(node.next.item);
                sb.append(", ");
                node = node.next;
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));

            sb.append("}");
        }
        return sb.toString();
    }

    /**
     * Проверка списка на цикличность.
     * @param first первый элемент.
     * @return true если есть цикличность.
     */
    boolean hasCycle(Node first) {
        Boolean result = false;
        Node checkNode = first;

        while (!result && checkNode.next != null) {
            if (checkNode.next == checkNode) {
                result = true;
            } else {
                Node tempNode = first;
                while (!result && checkNode != tempNode) {
                    if (checkNode.next == tempNode) {
                        result = true;
                    } else {
                        tempNode = tempNode.next;
                    }
                }
                checkNode = checkNode.next;
            }
        }
        return result;
    }

}
