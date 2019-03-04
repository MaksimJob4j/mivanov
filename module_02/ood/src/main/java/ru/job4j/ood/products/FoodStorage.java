package ru.job4j.ood.products;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(FoodStorage.class);
    private final Set<Food> foods = new HashSet<>();
    private String name;

    public void addFood(Food food) {
        LOGGER.traceEntry();
        this.foods.add(food);
    }

    public void removeFood(Food food) {
        LOGGER.traceEntry();
        this.foods.remove(food);
    }

}
