package ru.job4j.list;

/**
 * SimpleQueue.
 * @param <T> T.
 */
public class SimpleQueue<T> {

    /**
     * MyLinkedList.
     * @param <T> T.
     */
    private MyLinkedList<T> list = new MyLinkedList<>();

    /**
     * poll.
     * @return T.
     */
    public T poll() {
        if (list.getFirst() != null) {
            T result = list.getItem(list.getFirst());
            list.remove(list.getFirst());
            return result;
        } else {
            throw new MyEmptyQueueException("Очередь пуста");
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
