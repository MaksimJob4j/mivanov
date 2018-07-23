package ru.job4j.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    @Test
    public void whenAddThenItWorks() throws Exception {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThat(tree.add(null), is(false));
        assertThat(tree.add(10), is(true));
        assertThat(tree.add(10), is(false));
        assertThat(tree.add(15), is(true));
        assertThat(tree.add(15), is(false));
        assertThat(tree.add(16), is(true));
        assertThat(tree.add(17), is(true));
        assertThat(tree.add(14), is(true));
        assertThat(tree.add(9), is(true));
        assertThat(tree.add(8), is(true));

        assertThat(tree.isBinary(), is(true));
        System.out.println(tree.toString());
    }
}