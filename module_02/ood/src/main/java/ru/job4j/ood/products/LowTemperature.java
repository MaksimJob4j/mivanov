package ru.job4j.ood.products;

public interface LowTemperature {
    default boolean needLowTemperature() {
        return true;
    };
}
