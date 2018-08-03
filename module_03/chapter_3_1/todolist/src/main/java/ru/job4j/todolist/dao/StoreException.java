package ru.job4j.todolist.dao;

public class StoreException extends Exception {
    public StoreException(Exception e) {
        super(e);
    }
}
