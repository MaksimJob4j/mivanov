package ru.job4j.carpricespr.store;

public class NoCarException extends NoEntityException {
    public NoCarException(int id) {
        super("Car", id);
    }
}