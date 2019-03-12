package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.FoodStorage;

import java.time.LocalDateTime;

public class FoodStorageLimited extends FoodStorageDecorator {
    private final static Logger LOGGER = LogManager.getLogger(FoodStorageLimited.class);

    private boolean full;

    public FoodStorageLimited(FoodStorage foodStorage) {
        super(foodStorage);
    }

    public boolean isFull() {
        LOGGER.traceEntry();
        return this.full;
    }

    public void setFull(boolean full) {
        LOGGER.traceEntry();
        this.full = full;
    }

    @Override
    public boolean isFit(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        return !this.full && super.isFit(food, checkTime);
    }

}
