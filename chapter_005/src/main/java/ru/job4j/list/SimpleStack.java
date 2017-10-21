package ru.job4j.list;

/**
 * SimpleStack.
 * @param <T> T.
 */
public class SimpleStack<T> {

    /**
     * MyLinkedList.
     */
    private MyLinkedList<T> list = new MyLinkedList<>();

    /**
     * poll.
     * @return T.
     */
    public T poll() {
        if (list.getLast() != null) {
            T result = list.getItem(list.getLast());
            list.remove(list.getLast());
            return result;
        } else {
            throw new MyEmptyStackException("Стек пуст");
        }
    }

    /**
     * push.
     * @param value T.
     */
    public void push(T value) {
        list.add(value);

    }

    @Override
    public String toString() {
        return list.toString();
    }
}
