package ru.job4j.loop;

/**
 * Factorial.
 */
public class Factorial {
    /**
     *
     * @param n n.
     * @return Факториал числа n.
     */
    public int calc(int n) {
        if (n == 0) {
            return 1;
        }
        return n * calc(n - 1);
    }

}
