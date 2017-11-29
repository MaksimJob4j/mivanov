/**
 * 2. Реализовать ThreadPool [#1099]

 1. Инициализация пула должна быть по количеству ядер в системе.
 2. создать метод add(Work work).
 3. Если есть свободные треды, начать выполнение работы. если нет.
 то в очередь до появления свободного треда.

 */
package ru.job4j.threads;

public class ThreadPool<W extends Runnable> {

    private final int maxTreadCount = Runtime.getRuntime().availableProcessors();

    private int threadCount = 0;
    private int activeTread = 0;
    private int nextTread = 0;

    private final Object lock = new Object();

    public void add(W work) {

        new Thread(() -> {

            int threadNumber;

            synchronized (lock) {
                threadNumber = threadCount++;

                while (nextTread != threadNumber || activeTread >= maxTreadCount) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                activeTread++;
                nextTread++;

                lock.notifyAll();
            }

            work.run();

            synchronized (lock) {
                activeTread--;
                lock.notifyAll();
            }

        }).start();
    }
}
