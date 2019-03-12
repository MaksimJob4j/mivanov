package ru.job4j.ood.products;

public interface Recyclable {
    default boolean canRecycle() {
        return true;
    }
}
