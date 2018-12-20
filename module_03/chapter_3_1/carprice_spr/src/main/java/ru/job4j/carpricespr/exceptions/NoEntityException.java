package ru.job4j.carpricespr.exceptions;

public class NoEntityException extends Exception {
    private final String message;

    public NoEntityException(String className, int id) {
        this.message = "Entity of " + className + " with id=" + id + " not found!";
    }

    public String getMessage() {
        return this.message;
    }
}