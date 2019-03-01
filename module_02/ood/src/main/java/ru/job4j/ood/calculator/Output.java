package ru.job4j.ood.calculator;

public interface Output extends AutoCloseable {
    void print(String string);
}
