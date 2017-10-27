package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TreeTest {
    @Test
    public void whenAddThenItWorks() throws Exception {
        Tree<String> tree = new Tree<>();
        assertThat(tree.add(null, null), is(false));
        assertThat(tree.add(null, "a"), is(false));
        assertThat(tree.add("a", "a"), is(false));
        assertThat(tree.add("a", null), is(true));
        assertThat(tree.add("a", "aa"), is(true));
        assertThat(tree.add("a", null), is(false));
        assertThat(tree.add("a", "aa"), is(false));
        assertThat(tree.add("a", "bb"), is(true));
        assertThat(tree.add("bb", "bbb"), is(true));
        assertThat(tree.add("c", "cc"), is(false));


    }

    @Test
    public void whenCallContainsThenItWorks() throws Exception {
        Tree<String> tree = new Tree<>();
        tree.add("a", "aa");
        assertThat(tree.contains("a"), is(true));
        assertThat(tree.contains("aa"), is(true));
        assertThat(tree.contains("b"), is(false));
        assertThat(tree.contains(null), is(false));
    }

    @Test
    public void whenCallIteratorThenItWorks() throws Exception {
        Tree<String> tree = new Tree<>();
        tree.add("a", "aa");
        tree.add("a", "bb");
        Iterator<String> it = tree.iterator();
        it.next();
        it.next();
        assertThat(it.hasNext(), is(true));
        it.next();
        assertThat(it.hasNext(), is(false));


    }

}