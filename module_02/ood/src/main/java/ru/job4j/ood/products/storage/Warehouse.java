package ru.job4j.ood.products.storage;

import ru.job4j.ood.products.AbstractFoodStorage;
import ru.job4j.ood.products.FoodStorage;

public class Warehouse extends AbstractFoodStorage {

    public Warehouse(FoodStorage foodStorage, double maxLifeCriteria) {
        super(foodStorage, 0, maxLifeCriteria);
    }

}
