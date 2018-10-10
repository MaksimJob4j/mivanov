package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DoubleStackBasedQueueTest {

    @Test
    public void whenNewStackPollNull() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        assertNull(queue.poll());
    }

    @Test
    public void whenPushNullThenPollNull() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        queue.push(null);
        assertNull(queue.poll());
    }

    @Test
    public void whenPushOneThenPollOne() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        queue.push(1);
        assertThat(queue.poll(), is(1));
        assertNull(queue.poll());
    }

    @Test
    public void whenPushOneTwoThenPollOneTwo() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        queue.push(1);
        queue.push(2);
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
        assertNull(queue.poll());
    }

    @Test
    public void whenPushOneTwoThreeThenPollOneTwoThree() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
        assertNull(queue.poll());
    }

    @Test
    public void whenRandomPushAndPollThenCorrectReturn() {
        DoubleStackBasedQueue<Integer> queue = new DoubleStackBasedQueue<>();
        queue.push(1);
        queue.push(2);
        assertThat(queue.poll(), is(1));
        queue.push(3);
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
        assertNull(queue.poll());
    }
}