package ru.job4j.threads.switcher;

public class Buffer {
    private String string = "";

    public void add(int i) {
        string = string + i;
    }

    public String getString() {
        return string;
    }
}
