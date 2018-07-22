package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class MyHashSetTest {

    @Test
    public void whenAddAndRemoveElementsThenDoIt() {
        MyHashSet<String> set = new MyHashSet<>();
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

        System.out.println(set);


        for (int i = 0; i < 100; i++) {
            set.add("bbb" + i);
        }

        System.out.println(set);

    }


}