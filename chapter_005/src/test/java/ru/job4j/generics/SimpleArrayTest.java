package ru.job4j.generics;

import org.junit.Test;


import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * тест.
 */
public class SimpleArrayTest {

    /**
     * Тестовый класс.
     */
    public class User {
        /**
         * name.
         */
        private String name;

        /**
         * Конструктор.
         * @param name name.
         */
        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Тест конструктора SimpleArray(T[] array).
     */
    @Test
    public void whenNewSimpleArrayWithArrayThenGetIt() {
        User[] users = new User[] {
                new User("Max"),
                new User("Den") };
        SimpleArray<User> userSimpleArray = new SimpleArray<>(users);
        String result = userSimpleArray.toString();
        String expected = users.toString();
        assertThat(result, is(expected));

    }

     /**
     * Тест add(T t).
     */
    @Test
    public void whenAddToSimpleArrayWithSizeThenSimpleArrayHasIt() {
        SimpleArray<User> userSimpleArray = new SimpleArray<>();
        User max = new User("Max");

        userSimpleArray.add(max);
        User result = userSimpleArray.get(0);

        assertThat(result, is(max));

    }

    /**
     * Тест delete(int i).
     */
    @Test
    public void whenAddDeleteByIndexFromSimpleArrayThenSimpleArrayHasNotIt() {
        SimpleArray<User> userSimpleArray = new SimpleArray<>();
        User max = new User("Max");
        User den = new User("Den");

        userSimpleArray.add(max);
        userSimpleArray.add(den);
        userSimpleArray.delete(0);
        User result = userSimpleArray.get(0);

        assertThat(result, is(den));
    }

    /**
     * Тест delete(T t).
     */
    @Test
    public void whenAddDeleteFromSimpleArrayThenSimpleArrayHasNotIt() {
        SimpleArray<User> userSimpleArray = new SimpleArray<>();
        User max = new User("Max");
        User den = new User("Den");

        userSimpleArray.add(max);
        userSimpleArray.add(den);
        userSimpleArray.delete(max);
        User result = userSimpleArray.get(0);

        assertThat(result, is(den));

    }

}