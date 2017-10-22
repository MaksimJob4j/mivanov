package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class SimpleLinkedSetTest {

    /**
     * Test add.
     */
    @Test
    public void whenAddItemsThenTheyAreInSetWithoutDuplicates() {
        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("qqq");
        set.add("www");
        set.add(null);
        set.add("qqq");
        set.add("qqq");
        set.add("eee");
        String result = set.toString();
        assertThat(result, is("[qqq, www, eee]"));
    }

    /**
     * Test iterator.
     */
    @Test
    public void whenIteratorCallThenItWorks() {
        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        Iterator it = set.iterator();
        String result = "";
        while (it.hasNext()) {
            result += it.next().toString();
        }

        assertThat(result, is("123"));
    }

}