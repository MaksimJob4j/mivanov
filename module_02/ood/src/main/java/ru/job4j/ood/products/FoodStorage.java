package ru.job4j.ood.products;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(FoodStorage.class);
    private final Set<Food> foods = new HashSet<>();
    private String name;
    private FoodStorage chainStorage;
    private double lifeCriteria;

    public FoodStorage(FoodStorage chainStorage, double lifeCriteria) {
        this.chainStorage = chainStorage;
        this.lifeCriteria = lifeCriteria;
    }

    public boolean isFit(double lifePercentage) {
        return lifePercentage < this.lifeCriteria;
    }

    public void addFood(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        double lifePercentage = food.getLifePercentage(checkTime);
        if (isFit(lifePercentage)) {
            if (!this.foods.contains(food)) {
                if (food.getStorage() != null) {
                    food.getStorage().removeFood(food);
                }
                food.setStorage(this);
                foods.add(food);
            }
        } else {
            this.chainStorage.addFood(food, checkTime);
        }
    }

    public void removeFood(Food food) {
        LOGGER.traceEntry();
        this.foods.remove(food);
    }

}
