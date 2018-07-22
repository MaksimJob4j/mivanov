package ru.job4j.threads;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolNewTest {
    @Test
    public void addTest() throws Exception {
        ThreadPoolNew<Work> threadPool = new ThreadPoolNew<>();
        Thread thread;

        for (int i = 0; i < 10; i++) {
            Work work = new Work(String.valueOf(i));
            threadPool.add(work);
        }
        Thread.sleep(10000);

    }

}

