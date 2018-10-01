package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DoubleStackBasedQueueTest {

    @Test
    public void doubleStackBasedQueueTest() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        assertNull(queue.poll());
        queue.push(null);
        assertNull(queue.poll());
        queue.push(1);
        assertThat(queue.poll(), is(1));
        assertNull(queue.poll());
        queue.push(2);
        queue.push(3);
        assertThat(queue.poll(), is(2));
        queue.push(4);
        queue.push(5);
        assertThat(queue.poll(), is(3));
        assertThat(queue.poll(), is(4));
        queue.push(6);
        assertThat(queue.poll(), is(5));
        assertThat(queue.poll(), is(6));
        assertNull(queue.poll());
    }
}