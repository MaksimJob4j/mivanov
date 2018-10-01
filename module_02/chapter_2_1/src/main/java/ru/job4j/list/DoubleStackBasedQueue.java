/**
 * 5.3.3.1 Очередь на двух стеках [#160]
 *   Администратор,  15.09.18 20:03
 *
 * Нужно реализовать очередь.
 *
 * public class SimpleQueue<T> {
 *    public <T> poll()
 *
 *    public void push(T value);
 * }
 *
 * метод poll - должен возвращать значение и удалять его из коллекции.
 * Внутри очереди нужно использовать Стеки - [#71474]
 * Описание Queue - очередь. Описывается FIFO - first input first output.
 * То есть, первый зашел и первый вышел. Например.
 *
 * push(1);
 * push(2);
 * push(3);
 *
 * poll() - 1
 * poll() - 2
 * poll() - 3
 */
package ru.job4j.list;

import java.util.Stack;

public class DoubleStackBasedQueue<T> {

    private Stack<T> first = new Stack<>();
    private Stack<T> last = new Stack<>();

    public T poll() {
        moveStack(last, first);
        return first.empty() ? null : first.pop();
    }

    public void push(T value) {
        moveStack(first, last);
        first.push(value);
    }

    private void moveStack(Stack<T> from, Stack<T> to) {
        while (!from.empty()) {
            to.push(from.pop());
        }
    }
}
