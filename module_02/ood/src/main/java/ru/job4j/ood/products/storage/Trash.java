package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.FoodStorage;

public class Trash extends FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Trash.class);

    public Trash(FoodStorage chainStorage, double lifeCriteria) {
        super(chainStorage, lifeCriteria);
    }

    @Override
    public boolean isFit(double lifePercentage) {
        return true;
    }
}
