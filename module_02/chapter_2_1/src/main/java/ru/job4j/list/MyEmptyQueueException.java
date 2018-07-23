package ru.job4j.list;

/**
 * MyEmptyQueueException.
 */
public class MyEmptyQueueException extends Error {
    /**
     * Конструктор.
     * @param msg сообщение.
     */
    public MyEmptyQueueException(String msg) {
        super(msg);
    }
}
