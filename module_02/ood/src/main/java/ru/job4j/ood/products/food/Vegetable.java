package ru.job4j.ood.products.food;

import ru.job4j.ood.products.AbstractFood;
import ru.job4j.ood.products.LowTemperature;

import java.time.LocalDateTime;

public class Vegetable extends AbstractFood implements LowTemperature {

    public Vegetable(String name, LocalDateTime createDate, LocalDateTime expiryDate) {
        super(name, createDate, expiryDate);
    }

}
