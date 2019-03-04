package ru.job4j.ood.products.food;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;

import java.time.LocalDateTime;

public class Milk extends Food {
    private final static Logger LOGGER = LogManager.getLogger(Milk.class);

    public Milk(String name, LocalDateTime createDate, LocalDateTime expiryDate) {
        super(name, createDate, expiryDate);
    }
}
