package ru.job4j.threads;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CountTest {
    @Test
    public void increment() throws Exception {
        Count count = new Count();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(count));
        }
        for (Thread thread: threads) {
            thread.run();
        }

        int result = count.getCount();

        assertThat(result, is(10));
    }

}