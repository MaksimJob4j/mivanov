package ru.job4j.threads;

import java.util.LinkedList;

/**
 * Структура данных блокирующей очереди.
 * @param <E> Тип данных элементов очереди.
 */
public class ProducerCustomer<E> {

    private final LinkedList<E> container = new LinkedList<>();

    /**
     * Добавление данных.
     * null данные игнорируются.
     * @param element элемент типа E.
     */
    public void produce(E element) {
        if (element != null) {
            synchronized (this.container) {
                this.container.offerFirst(element);
                this.container.notify();
            }
        }
    }

    /**
     * Извлечение элемента.
     * @return элемент данных типа E.
     * @throws InterruptedException InterruptedException.
     */
    public E customer() throws InterruptedException {
        E result;
        synchronized (this.container) {
            while (this.container.peekLast() == null) {
                container.wait();
            }
            result = this.container.pollLast();
            return result;
        }
    }
}
