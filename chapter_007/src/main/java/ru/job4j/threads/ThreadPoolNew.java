/**
 * 2. Реализовать ThreadPool [#1099]

 1. Инициализация пула должна быть по количеству ядер в системе.
 2. создать метод add(Work work).
 3. Если есть свободные треды, начать выполнение работы. если нет.
 то в очередь до появления свободного треда.

 */
package ru.job4j.threads;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolNew<W extends Runnable> {

    private final int maxTreadCount = Runtime.getRuntime().availableProcessors();

    private LinkedBlockingQueue<W> works = new LinkedBlockingQueue<>();

    private final Object lock = new Object();

    public ThreadPoolNew() {
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < maxTreadCount; i++) {
            new Thread(() -> {
                Runnable work;
                while (true) {
                    work = works.poll();
                    if (work != null) {
                        work.run();
                    } else {
                        synchronized (lock) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }).start();
        }
    }

    public void add(W work) {
        works.add(work);
        synchronized (lock) {
            lock.notify();
        }
    }
}
