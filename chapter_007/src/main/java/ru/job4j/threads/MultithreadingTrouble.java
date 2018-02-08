/**
 * 1. Проиллюстрировать проблемы с многопоточностью. [#1096]
 *
 * 1000 потоков увеличивают одну и ту же переменную 100 раз на 1
 * Ожидаемый результат - 100 0000.
 * Полученное значение в силу race condition меньше ожидаемого.
 *
 */
package ru.job4j.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultithreadingTrouble {
    int i = 0;

    private void increment() {
        i++;
    }

    private void getTroubleExecutor() {
        this.i = 0;
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            exec.submit(getIncrementThread());
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                exec.awaitTermination(100, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("1000 * 1000 = %s", i);
    }

    private void getTroubleJoin() {
        this.i = 0;
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = getIncrementThread();
            threads[i].start();
        }
        for (Thread tr: threads) {
            try {
                tr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("1000 * 1000 = %s", i);
    }

    private Thread getIncrementThread() {
        return new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        increment();
                    }
                });
    }

    public static void main(String[] args) {
        MultithreadingTrouble trouble = new MultithreadingTrouble();
        trouble.getTroubleExecutor();
        System.out.println();
        trouble.getTroubleJoin();
    }
}
