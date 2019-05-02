package ru.job4j.gc;

public class User {
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalized " + name);
    }
}
