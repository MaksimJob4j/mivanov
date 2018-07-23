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
     * poll Objects.
     */
    @Test
    public void whenPollAllItemsFromQueueThenQueueDoesNotHaveItAnymore() {
        SimpleQueue queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
        queue.push(6);
        queue.push(7);
        assertThat(queue.poll(), is(4));
        assertThat(queue.poll(), is(5));
        queue.push(8);
        assertThat(queue.poll(), is(6));
        assertThat(queue.poll(), is(7));
        assertThat(queue.poll(), is(8));

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