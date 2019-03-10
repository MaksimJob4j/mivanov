package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.AbstractFoodStorage;
import ru.job4j.ood.products.FoodStorage;

public class Trash extends AbstractFoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Trash.class);

    public Trash() {
        super(null, 1);
    }
}
