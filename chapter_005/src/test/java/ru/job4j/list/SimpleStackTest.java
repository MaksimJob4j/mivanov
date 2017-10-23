package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class SimpleStackTest {

    /**
     * push.
     */
    @Test
    public void whenPushToStackThenStackHasIt() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("item1");
        stack.push("item2");
        String result = stack.toString();
        assertThat(result, is("{item1, item2}"));
    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromStackThenStackDoesNotHaveItAnymore() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("item1");
        stack.push("item2");
        stack.poll();
        String result = stack.toString();
        assertThat(result, is("{item1}"));
    }

    /**
     * poll Objects.
     */
    @Test
    public void whenPollAllItemsFromQueueThenQueueDoesNotHaveItAnymore() {
        SimpleStack stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertThat(stack.poll(), is(5));
        assertThat(stack.poll(), is(4));
        assertThat(stack.poll(), is(3));
        stack.push(6);
        stack.push(7);
        assertThat(stack.poll(), is(7));
        assertThat(stack.poll(), is(6));
        stack.push(8);
        assertThat(stack.poll(), is(8));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));

    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromStackThenStackReturnLastItem() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("item1");
        stack.push("item2");
        String result = stack.poll();
        assertThat(result, is("item2"));
    }

    /**
     * poll.
     */
    @Test
    public void whenPollFromStackSingleItemThenStackReturnItem() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push(null);
        stack.poll();
        String result = "";
        try {
            stack.poll();
        } catch (MyEmptyStackException mese) {
            result = mese.getMessage();
        }
        assertThat(result, is("Стек пуст"));
    }

}