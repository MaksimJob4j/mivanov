package ru.job4j.threads;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProducerCustomerTest {

    @Test
    public void test() throws InterruptedException {
        int n = 10;
        ProducerCustomer<String> queue = new ProducerCustomer<>();
//        System.out.println(queue.customer());
//        queue.produce("string-1");
//        System.out.println(queue.customer());
//        queue.produce("string-2");

        class Producer extends Thread {

            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    String st = String.format("%s-%s", Thread.currentThread().getId(), i);
                    System.out.println(String.format("Tread-%s produce %s", Thread.currentThread().getId(), st));
                    queue.produce(st);
                }

            }
        }

        class Customer extends Thread {

            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    try {
                        System.out.println(String.format("Tread-%s customer %s", Thread.currentThread().getId(), queue.customer()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            new Customer().start();
        }

        Thread.sleep(2000);

        for (int i = 0; i < 3; i++) {
            new Producer().start();
        }

        Thread.sleep(1000);

    }

}