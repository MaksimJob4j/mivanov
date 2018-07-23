package ru.job4j.threads.bomberman;

public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    System.out.println(i++);
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.interrupted());
                        return;
                    }
                }
            }
        });
        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
