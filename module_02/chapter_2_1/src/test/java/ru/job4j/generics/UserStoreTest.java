package ru.job4j.generics;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class UserStoreTest {

    /**
     * UserStoreAdd.
     */
    @Test
    public void whenCreateUserStoreThenCanAddUser() {

        UserStore userStore = new UserStore();
        User user = new User();
        user.setId("qwerty");
        userStore.add(user);

        User result = userStore.get(0);
        assertThat(result, is(user));
    }

    /**
     * UserStoreDelete.
     */
    @Test
    public void whenCreateUserStoreThenCanDeleteUser() {

        UserStore userStore = new UserStore();
        User user1 = new User();
        User user2 = new User();
        user1.setId("qwerty1");
        user2.setId("qwerty2");
        userStore.add(user1);
        userStore.add(user2);
        userStore.delete(user1.getId());

        User result = userStore.get(0);
        assertThat(result, is(user2));
    }


    /**
     * UserStoreUpdate.
     */
    @Test
    public void whenCreateUserStoreThenCanUpdateUser() {

        UserStore userStore = new UserStore();
        User user1 = new User();
        User user2 = new User();
        user1.setId("qwerty1");
        userStore.add(user1);
        user2.setId("qwerty1");
        userStore.update(user2);

        User result = userStore.get(0);
        assertThat(result, is(user2));
    }

}