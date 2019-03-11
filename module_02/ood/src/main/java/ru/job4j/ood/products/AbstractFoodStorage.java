package ru.job4j.ood.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractFoodStorage implements FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(AbstractFoodStorage.class);
    private final Set<Food> foods = new HashSet<>();
//    private String name;
    private final FoodStorage chainStorage;
    private final double minLifeCriteria;
    private final double maxLifeCriteria;

    public AbstractFoodStorage(FoodStorage chainStorage, double minLifeCriteria, double maxLifeCriteria) {
        this.chainStorage = chainStorage;
        this.minLifeCriteria = minLifeCriteria;
        this.maxLifeCriteria = maxLifeCriteria;
    }

    public AbstractFoodStorage(FoodStorage chainStorage, double minLifeCriteria) {
        this.chainStorage = chainStorage;
        this.minLifeCriteria = minLifeCriteria;
        this.maxLifeCriteria = 0;
    }

    @Override
    public boolean isFit(Food food, LocalDateTime checkTime) {
        double lifePercentage = food.getLifePercentage(checkTime);
        if (maxLifeCriteria == 0) {
            return lifePercentage > this.minLifeCriteria;
        } else {
            return lifePercentage > this.minLifeCriteria && lifePercentage <= this.maxLifeCriteria;
        }
    }

    @Override
    public void addFood(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        if (!this.foods.contains(food)) {
            if (food.getStorage() != null) {
                food.getStorage().removeFood(food);
            }
            food.setStorage(this);
            this.foods.add(food);
        }
    }

    @Override
    public void removeFood(Food food) {
        LOGGER.traceEntry();
        this.foods.remove(food);
        food.setStorage(null);
    }

    @Override
    public Set<Food> getFoods() {
        return this.foods;
    }

    @Override
    public FoodStorage getChainStorage() {
        return this.chainStorage;
    }
}
