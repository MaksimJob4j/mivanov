package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashSetLinkedElementsTest {

    @Test
    public void whenAddThenSetHasIt() {
        MyHashSetLinkedElements<String> set = new MyHashSetLinkedElements<>();
        for (int i = 0; i < 10; i++) {
            set.add("aaa" + i);
        }

        System.out.println(set);

        set.add("aaa");
        set.add("aaa");
        set.add("");
        set.add(null);

        System.out.println(set);

        for (int i = 0; i < 5; i++) {
            set.remove("aaa" + i);
        }

        set.remove(null);

        System.out.println(set);


//        здесь начинает выдавать ошибку
//        java.lang.OutOfMemoryError: Java heap space
        for (int i = 0; i < 10000; i++) {
            set.add("bbb" + i);
        }

        System.out.println(set);
    }

}