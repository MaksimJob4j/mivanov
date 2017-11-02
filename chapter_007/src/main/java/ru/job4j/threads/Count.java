package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count implements Runnable {

    @GuardedBy("count")
    private int count = 0;

    public int getCount() {
        return count;
    }

    @GuardedBy(value = "count")
    synchronized int increment() {
        return ++this.count;
    }


    @Override
    public void run() {
        this.increment();
    }
}
