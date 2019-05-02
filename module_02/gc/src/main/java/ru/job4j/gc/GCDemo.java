

package ru.job4j.gc;

public class GCDemo {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max: " + runtime.maxMemory());
        System.out.println("Total: " + runtime.totalMemory());
        System.out.println("Used: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("Free: " + runtime.freeMemory());

        int length = 30_000;
        for (int i = 0; i < 2; i++) {
            User[] users = new User[length];
            for (int j = 0; j < length; j++) {
                users[j] = new User(String.format("user_%05d", j));
            }
        }
    }
}
