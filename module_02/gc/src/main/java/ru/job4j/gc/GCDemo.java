

package ru.job4j.gc;

public class GCDemo {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max: " + runtime.maxMemory());
        System.out.println("Total: " + runtime.totalMemory());
        System.out.println("Used: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("Free: " + runtime.freeMemory());
        long time = System.currentTimeMillis();
        int length = 1_000;
        int cycles = 60;
        System.out.format("Total count - %d objects%n", length * cycles);
        for (int i = 0; i < cycles; i++) {
            User[] users = new User[length];
            for (int j = 0; j < length; j++) {
                users[j] = new User(String.format("user_%05d", j));
            }
        }
        System.out.format("Total time - %d ms%n", (System.currentTimeMillis() - time));
        System.out.format("Collect count - %d objects%n", User.COUNT.get());
        System.out.format("Garbage in memory - %d objects%n", (length * cycles - User.COUNT.get()));
    }
}
