package ru.job4j.carpricesprboot.exceptions;

public class NoCarException extends NoEntityException {
    public NoCarException(int id) {
        super("Car", id);
    }
}