package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.FoodStorage;

public class Warehouse extends FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Warehouse.class);

    public Warehouse(Shop shop, double lifeCriteria) {
        super(shop, lifeCriteria);
    }
}
