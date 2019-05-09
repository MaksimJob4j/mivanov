package ru.job4j.gc;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    public static final AtomicInteger COUNT = new AtomicInteger();
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        COUNT.incrementAndGet();
        super.finalize();
//        System.out.println("finalized " + name);
    }
}
