package ru.job4j.list;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class MyLinkedListTest {
    /**
     * Test.
     */
    @Test
    public void whenAddMyLinkedListThenMyLinkedListHasIt() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.add("s1");
        strings.add("s2");
        strings.add("s3");
        String result = strings.toString();
        assertThat(result, is("{s1, s2, s3}"));
    }

    /**
     * Test.
     */
    @Test
    public void whenDeleteByIndexThenMyLinkedListHasNoIt() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.add("s1");
        strings.add("s2");
        strings.add("s3");
        strings.remove(1);
        String result = strings.toString();
        assertThat(result, is("{s1, s3}"));
    }

    /**
     * Test.
     */
    @Test
    public void whenDeleteByIndexSingleElementThenMyLinkedListHasNoIt() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.add("s1");
        strings.remove(0);
        String result = strings.toString();
        assertThat(result, is(""));
    }

    /**
     * Test.
     */
    @Test
    public void whenGetByIndexThenMyLinkedListHasNoIt() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.add("s1");
        strings.add("s2");
        strings.add("s3");
        strings.get(1);
        String result = strings.get(1);
        assertThat(result, is("s2"));
    }

    /**
     * Test.
     */
    @Test
    public void whenCallIteratorThenItWork() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.add("s1");
        strings.add("s2");
        strings.add("s3");
        Iterator iterator = strings.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result += iterator.next();
        }
        assertThat(result, is("s1s2s3"));
    }


}