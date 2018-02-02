/**
 * 3. Реализовать собственный механизм блокировок Lock.  [#1100].
 *
 * Необходимо реализовать всего два метода:
 * 1) lock - проверяет свободен ли лок? Если да - захватывает, иначе блокируется.
 * 2) unlock - проверяет владеет ли поток локом? Если да то - освобождает.
 * Реентерабельность можно не реализовывать.
 *
 */
package ru.job4j.threads;

public class MyLock {

    private Boolean isLocked = false;
    Thread locker = null;

    public synchronized void lock() throws InterruptedException {
            while (!Thread.currentThread().equals(locker)) {
                System.out.println(Thread.currentThread().getName() + " пытается захватить лок");

                if (!isLocked) {
                    isLocked = true;
                    locker = Thread.currentThread();
                } else {
                    this.wait();
                }
            }
    }

    public synchronized void unlock() {
            isLocked = false;
            locker = null;
            this.notify();
    }

    public static void main(String[] args) {

        MyLock lock = new MyLock();

        for (int i = 0; i < 3; i++) {

            new Thread("Thread number " + i) {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        System.out.println("Лок захвачен " + Thread.currentThread().getName());
                        System.out.println("Ждем 3");
                        Thread.sleep(1000);
                        System.out.println("Ждем 2");
                        Thread.sleep(1000);
                        System.out.println("Ждем 1");
                        Thread.sleep(1000);
                        lock.unlock();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

}
