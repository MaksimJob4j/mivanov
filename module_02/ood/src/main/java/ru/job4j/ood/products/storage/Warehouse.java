package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.AbstractFoodStorage;
import ru.job4j.ood.products.FoodStorage;

public class Warehouse extends AbstractFoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Warehouse.class);

    public Warehouse(FoodStorage foodStorage, double maxLifeCriteria) {
        super(foodStorage, 0, maxLifeCriteria);
    }
}
