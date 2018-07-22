package ru.job4j.loop;

/**
 * Counter.
 */
public class Counter {
    /**
     *
     * @param start start.
     * @param finish finish.
     * @return Сумма четных чисел.
     */
    public int add(int start, int finish) {
        int counter = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                counter += i;
            }
        }
        return counter;
    }
}
