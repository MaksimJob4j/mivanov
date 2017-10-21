package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class SimpleQueueTest {
    /**
     * push.
     */
    @Test
    public void whenPushToQueueThenQueueHasIt() {
        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push("item1");
        queue.push("item2");
        String result = queue.toString();
        assertThat(result, is("{item1, item2}"));
    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromQueueThenQueueDoesNotHaveItAnymore() {
        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push("item1");
        queue.push("item2");
        queue.poll();
        String result = queue.toString();
        assertThat(result, is("{item2}"));
    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromQueueThenQueueReturnLastItem() {
        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push("item1");
        queue.push("item2");
        String result = queue.poll();
        assertThat(result, is("item1"));
    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromQueueSingleItemThenQueueReturnItem() {
        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push(null);
        queue.poll();
        String result = "";
        try {
            queue.poll();
        } catch (MyEmptyQueueException meqe) {
            result = meqe.getMessage();
        }
        assertThat(result, is("Очередь пуста"));
    }


}