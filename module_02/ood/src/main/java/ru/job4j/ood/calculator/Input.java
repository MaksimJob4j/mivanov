package ru.job4j.ood.calculator;

public interface Input extends AutoCloseable {
    String next();
    String askString(String question);
    double askDouble(String question) throws ParseException;
    void close();
}
