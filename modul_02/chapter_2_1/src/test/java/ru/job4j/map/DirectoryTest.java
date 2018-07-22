package ru.job4j.map;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DirectoryTest {

    @Test
    public void whenCallInsertThenGetIt() {
        Directory<Integer, User> directory = new Directory<>();
        User max = new User("Maxim");
        User nullUser = null;
        directory.insert(1, max);
        assertThat(directory.get(1), is(max));

    }


    @Test
    public void whenCallDeleteThenDeleteIt() {
        Directory<Integer, User> directory = new Directory<>();
        User max = new User("Maxim");
        directory.insert(1, max);
        assertThat(directory.delete(1), is(true));
        assertThat(directory.delete(1), is(false));
    }

    @Test
    public void whenCallIteratorThenItWorks() throws Exception {
        Directory<Integer, User> directory = new Directory<>();
        User max = new User("Maxim");
        User den = new User("Denis");
        directory.insert(1, max);
        directory.insert(2, den);
        Iterator<Integer> it = directory.iterator();
        it.next();
        assertThat(it.hasNext(), is(true));
        it.next();
        assertThat(it.hasNext(), is(false));

    }


}