package ru.job4j.threads.switcher;

public class Store {
    private String string = "";

    public void addChar(int i) {
        string = string + i;
    }

    public String getString() {
        return string;
    }
}
