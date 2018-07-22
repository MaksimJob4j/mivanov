package ru.job4j.threads;

import org.junit.Test;

import static org.junit.Assert.*;

public class SynchrLinkedListTest {
    @Test
    public void addTest() throws Exception {
        SynchrLinkedList<String> list = new SynchrLinkedList<>();
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread("thread-" + i) {

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        list.add(Thread.currentThread().getName() + " add " + j);
                        System.out.println(Thread.currentThread().getName() + " add " + j);
                    }
                }
            };
        }
        threads[0].start();
        threads[1].start();
        threads[2].start();
        Thread.sleep(1000);

    }

    @Test
    public void getTest() throws Exception {
        SynchrLinkedList<String> list = new SynchrLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread("thread-" + i) {

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        list.get(j);
                        System.out.println(Thread.currentThread().getName() + " get " + j + "-index");
                    }
                }
            };
        }
        threads[0].start();
        threads[1].start();
        threads[2].start();
        Thread.sleep(1000);
        System.out.println(list);

    }

    @Test
    public void removeTestIndex() throws Exception {
        SynchrLinkedList<String> list = new SynchrLinkedList<>();
        for (int i = 0; i < 300; i++) {
            list.add(String.valueOf(i));
        }
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread("thread-" + i) {

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        list.remove(0);
                        System.out.println(Thread.currentThread().getName() + " remove 0-index");
                    }
                }
            };
        }
        threads[0].start();
        threads[1].start();
        threads[2].start();
        Thread.sleep(1000);
        System.out.println(list);

    }
}