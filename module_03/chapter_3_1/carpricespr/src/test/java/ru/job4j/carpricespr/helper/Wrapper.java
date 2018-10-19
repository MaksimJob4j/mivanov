package ru.job4j.carpricespr.helper;

public interface Wrapper<T> {
    T get();
    void set(T value);
    boolean isEmpty();
}
