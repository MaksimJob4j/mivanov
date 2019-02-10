package ru.job4j.inout.chat;

public abstract class Author {

    private final String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract Message getMessage();
    void initAuthor() { };
}
