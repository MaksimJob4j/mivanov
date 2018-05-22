package ru.job4j.threads.switcher;

import java.util.concurrent.atomic.AtomicInteger;

public class Switcher {
    private final Store store = new Store();
    AtomicInteger counter = new AtomicInteger(0);

    public void makeString(int millisToString) {
        Thread thread1 = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    if (counter.get() < 10) {
                        store.addChar(1);
                        counter.incrementAndGet();
                    }
                }
        });
        Thread thread2 = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    if (counter.get() > 9) {
                        store.addChar(2);
                        if (counter.incrementAndGet() == 20) {
                            counter.set(0);
                        }
                    }
                }
        });
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(millisToString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        thread2.interrupt();
        System.out.println(store.getString());
    }

    public static void main(String[] args) {
        Switcher switcher = new Switcher();
        switcher.makeString(1);
    }
}
