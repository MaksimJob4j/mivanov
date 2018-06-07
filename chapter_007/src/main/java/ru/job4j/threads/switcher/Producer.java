package ru.job4j.threads.switcher;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Producer extends Thread {
    private final Buffer buffer;
    private final int value;
    private final ReentrantLock lock;
    private final AtomicInteger flag;

    public Producer(final Buffer buffer, final int value, AtomicInteger flag, ReentrantLock lock) {
        this.buffer = buffer;
        this.value = value;
        this.flag = flag;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            if (this.flag.get() != this.value) {
                IntStream.range(0, 10).forEach(
                        value -> buffer.add(this.value)
                );
                this.flag.set(this.value);
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final Buffer buffer = new Buffer();
        final AtomicInteger flag = new AtomicInteger(2);
        final ReentrantLock lock = new ReentrantLock(true);
        Thread thread1 = new Producer(buffer, 1, flag, lock);
        Thread thread2 = new Producer(buffer, 2, flag, lock);
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
