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