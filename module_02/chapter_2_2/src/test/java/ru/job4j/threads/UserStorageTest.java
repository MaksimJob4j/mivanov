package ru.job4j.threads;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void add() throws Exception {
        UserStorage storage = new UserStorage();

        assertThat(storage.add(new User(1, 10)), is(true));
        assertThat(storage.add(new User(1, 10)), is(false));
    }

    @Test
    public void update() throws Exception {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 10));

        assertThat(storage.update(new User(1, 20)), is(true));
        assertThat(storage.update(new User(2, 20)), is(false));

    }

    @Test
    public void delete() throws Exception {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 10));

        assertThat(storage.delete(new User(1, 20)), is(true));
        assertThat(storage.delete(new User(1, 20)), is(false));

    }

    @Test
    public void transfer() throws Exception {
        UserStorage storage = new UserStorage();

        assertThat(storage.transfer(1, 2, 10), is(false));
        storage.add(new User(1, 0));
        assertThat(storage.transfer(1, 2, 10), is(false));
        storage.add(new User(2, 0));
        assertThat(storage.transfer(1, 2, 10), is(false));
        storage.update(new User(1, 10));
        assertThat(storage.transfer(1, 2, 10), is(true));


    }

}