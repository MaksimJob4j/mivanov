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
        System.out.println(Thread.currentThread().getName() + " пытается снять блокировку");

        if (Thread.currentThread().equals(locker)) {
            isLocked = false;
            locker = null;
            System.out.println(Thread.currentThread().getName() + " снял блокировку");

        } else {
            System.out.println(Thread.currentThread().getName() + " не смог снять блокировку");

        }

        this.notifyAll();
    }

    public static void main(String[] args) {

        MyLock lock = new MyLock();

        for (int i = 0; i < 3; i++) {

            new Thread("Thread number " + i) {
                @Override
                public void run() {
                    try {
//                        lock.unlock();
                        lock.lock();
                        System.out.println("Лок захвачен " + Thread.currentThread().getName());
                        System.out.println("Ждем 3");
                        Thread.sleep(1000);
                        System.out.println("Ждем 2");
                        Thread.sleep(1000);
                        System.out.println("Ждем 1");
                        Thread.sleep(1000);
                        lock.unlock();
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
