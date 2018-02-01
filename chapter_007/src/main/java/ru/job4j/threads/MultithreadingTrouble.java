/**
 * 1. Проиллюстрировать проблемы с многопоточностью. [#1096]
 *
 * 1000 потоков увеличивают одну и ту же переменную 100 раз на 1
 * Ожидаемый результат - 100 0000.
 * Полученное значение в силу race condition меньше ожидаемого.
 *
 */
package ru.job4j.threads;

public class MultithreadingTrouble {
    int i = 0;

    private void increment() {
        i++;
    }

    private void getTrouble() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    increment();
                }
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.printf("1000 * 100 = %s", i);

    }

    public static void main(String[] args) {
        MultithreadingTrouble trouble = new MultithreadingTrouble();
        trouble.getTrouble();

    }
}
