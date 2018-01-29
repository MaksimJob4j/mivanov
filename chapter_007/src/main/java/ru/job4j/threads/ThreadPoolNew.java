/**
 * 2. Реализовать ThreadPool [#1099]

 1. Инициализация пула должна быть по количеству ядер в системе.
 2. создать метод add(Work work).
 3. Если есть свободные треды, начать выполнение работы. если нет.
 то в очередь до появления свободного треда.

 */
package ru.job4j.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolNew<W extends Runnable> {

    private final int maxTreadCount = Runtime.getRuntime().availableProcessors();

    private LinkedBlockingQueue<W> works = new LinkedBlockingQueue<>();

    private boolean isStarted = false;

    private void initialize() {
        for (int i = 0; i < maxTreadCount; i++) {
            new Thread(() -> {
                Runnable work;
                while (true) {
                    try {
                        work = works.poll(1L, TimeUnit.SECONDS);
                        if (work != null) {
                            work.run();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }

    public void add(W work) {
        if (!isStarted) {
            this.initialize();
            this.isStarted = true;
        }
        works.add(work);
    }
}
