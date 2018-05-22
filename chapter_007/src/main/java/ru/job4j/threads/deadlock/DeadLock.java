package ru.job4j.threads.deadlock;

public class DeadLock {
    Object object1 = new Object();
    Object object2 = new Object();
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            synchronized (object1) {
                System.out.println("first start");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2) {
                    System.out.println("first");
                }
            }
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            synchronized (object2) {
                System.out.println("second start");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println("second");
                }
            }
        }
    };

    public void start() {
        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

    public static void main(String[] args) {
        new DeadLock().start();
    }
}
