package ru.job4j.threads;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolTest {
    @Test
    public void addTest() throws Exception {
        ThreadPool<Work> threadPool = new ThreadPool<>();
        Thread thread;

        for (int i = 0; i < 10; i++) {
            Work work = new Work(String.valueOf(i));
            threadPool.add(work);
        }
        Thread.sleep(10000);

    }

}

class Work implements Runnable {
    private String name;

    public Work(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(String.format("Work-%s start", name));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Work-%s stop", name));
    }

}