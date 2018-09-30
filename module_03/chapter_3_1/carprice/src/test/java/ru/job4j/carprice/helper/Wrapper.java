package ru.job4j.carprice.helper;

public interface Wrapper<T> {
    T get();
    void set(T value);
    boolean isEmpty();
}
