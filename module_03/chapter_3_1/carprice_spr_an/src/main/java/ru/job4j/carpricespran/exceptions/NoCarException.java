package ru.job4j.carpricespran.exceptions;

public class NoCarException extends NoEntityException {
    public NoCarException(int id) {
        super("Car", id);
    }
}