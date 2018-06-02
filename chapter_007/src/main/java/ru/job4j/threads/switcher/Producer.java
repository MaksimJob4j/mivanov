package ru.job4j.threads.switcher;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Producer extends Thread {
    private final Buffer buffer;
    private final int value;
    private final AtomicInteger lock;

    public Producer(final Buffer buffer, final int value, AtomicInteger lock) {
        this.buffer = buffer;
        this.value = value;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (this.lock.get() != this.value) {
                IntStream.range(0, 10).forEach(
                        value -> buffer.add(this.value)
                );
                this.lock.set(this.value);
            } else {
                    Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        final Buffer buffer = new Buffer();
        final AtomicInteger flag = new AtomicInteger(2);
        Thread thread1 = new Producer(buffer, 1, flag);
        Thread thread2 = new Producer(buffer, 2, flag);
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        thread2.interrupt();

        String s = buffer.getString();
        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (i % 10 == 0) {
                System.out.println();
            }
            System.out.print(c[i]);
        }
    }
}
