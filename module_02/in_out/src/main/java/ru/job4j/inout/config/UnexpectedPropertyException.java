package ru.job4j.inout.config;

public class UnexpectedPropertyException extends Exception {

    public UnexpectedPropertyException(String errorMessage) {
        super(errorMessage);
    }
}
