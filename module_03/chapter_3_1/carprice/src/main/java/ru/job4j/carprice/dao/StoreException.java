package ru.job4j.carprice.dao;

public class StoreException extends Exception {
    public StoreException(Exception e) {
        super(e);
    }
}