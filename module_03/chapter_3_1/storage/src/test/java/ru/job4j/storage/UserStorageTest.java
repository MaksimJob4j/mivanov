package ru.job4j.storage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-storage.xml");
    private final UserStorage storage = (UserStorage) context.getBean("userStorageMem");

    @Test
    public void create() {
        User user = new User();
        user.setLogin("user1");
        assertThat(user.getId(), is(0));
        storage.create(user);
        assertTrue(user.getId() != 0);
    }

    @Test
    public void update() {
        User user = new User();
        user.setLogin("user2");
        storage.create(user);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setLogin("user3");
        assertThat(storage.find(user.getId()).getLogin(), is("user2"));
        storage.update(newUser);
        assertThat(storage.find(user.getId()).getLogin(), is("user3"));
    }

    @Test
    public void delete() {
        User user = new User();
        user.setLogin("user4");
        storage.create(user);
        assertNotNull(storage.find(user.getId()));
        storage.delete(user);
        assertNull(storage.find(user.getId()));
    }

    @Test
    public void find() {
        User user = new User();
        user.setLogin("user5");
        storage.create(user);
        assertThat(storage.find(user.getId()), is(user));
    }

    @Test
    public void findAll() {
        User user = new User();
        user.setLogin("user6");
        User newUser = new User();
        user.setLogin("user7");
        assertFalse(storage.find().contains(user));
        assertFalse(storage.find().contains(newUser));
        storage.create(user);
        storage.create(newUser);
        assertTrue(storage.find().contains(user));
        assertTrue(storage.find().contains(newUser));
    }
}