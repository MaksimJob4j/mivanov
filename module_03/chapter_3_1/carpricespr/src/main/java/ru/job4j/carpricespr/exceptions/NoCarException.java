package ru.job4j.carpricespr.exceptions;

public class NoCarException extends NoEntityException {
    public NoCarException(int id) {
        super("Car", id);
    }
}