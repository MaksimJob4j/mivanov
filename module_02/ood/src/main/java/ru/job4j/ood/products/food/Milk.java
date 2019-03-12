package ru.job4j.ood.products.food;

import ru.job4j.ood.products.AbstractFood;

import java.time.LocalDateTime;

public class Milk extends AbstractFood {

    public Milk(String name, LocalDateTime createDate, LocalDateTime expiryDate) {
        super(name, createDate, expiryDate);
    }
}
