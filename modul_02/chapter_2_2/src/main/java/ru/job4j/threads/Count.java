package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count implements Runnable {

    @GuardedBy("this")
    private int count = 0;

    synchronized int getCount() {
        return count;
    }

    synchronized int increment() {
        return ++this.count;
    }

    @Override
    public void run() {
        this.increment();
    }
}
