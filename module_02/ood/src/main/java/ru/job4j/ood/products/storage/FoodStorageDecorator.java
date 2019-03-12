package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.FoodStorage;

import java.time.LocalDateTime;
import java.util.Set;

public abstract class FoodStorageDecorator implements FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(FoodStorageDecorator.class);

    private final FoodStorage foodStorage;

    public FoodStorageDecorator(FoodStorage foodStorage) {
        this.foodStorage = foodStorage;
    }

    @Override
    public boolean isFit(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        return this.foodStorage.isFit(food, checkTime);
    }

    @Override
    public void addFood(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        this.foodStorage.addFood(food, checkTime);
    }

    @Override
    public void removeFood(Food food) {
        LOGGER.traceEntry();
        this.foodStorage.removeFood(food);
    }

    @Override
    public Set<Food> getFoods() {
        LOGGER.traceEntry();
        return this.foodStorage.getFoods();
    }

    @Override
    public FoodStorage getChainStorage() {
        LOGGER.traceEntry();
        return this.foodStorage.getChainStorage();
    }

}
