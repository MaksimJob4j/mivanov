package ru.job4j.carpricesprboot.repository.exceptions;

public class NoCarException extends NoEntityException {
    public NoCarException(int id) {
        super("Car", id);
    }
}