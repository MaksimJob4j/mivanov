package ru.job4j.threads.deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    private final Object object1 = new Object();
    private final Object object2 = new Object();
    private Runnable runnable1 = () -> {
        synchronized (object1) {
            lock1.tryLock();
            System.out.println("First get object1 and lock1");
            while (!lock2.isLocked()) {
                Thread.yield();
            }
            System.out.println("First try get object2");
            synchronized (object2) {
                System.out.println("First get object2");
            }
            lock1.unlock();
        }
    };
    private Runnable runnable2 = () -> {
        synchronized (object2) {
            lock2.tryLock();
            System.out.println("Second get object2 and lock2");
            while (!lock1.isLocked()) {
                Thread.yield();
            }
            System.out.println("Second try get object1");
            synchronized (object1) {
                System.out.println("Second get object1");
            }
            lock2.unlock();
        }
    };

    public void start() {
        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

    public static void main(String[] args) {
        new DeadLock().start();
    }
}
