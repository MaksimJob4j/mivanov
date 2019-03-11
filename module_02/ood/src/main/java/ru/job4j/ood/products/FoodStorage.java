package ru.job4j.ood.products;

import java.time.LocalDateTime;
import java.util.Set;

public interface FoodStorage {

    boolean isFit(Food food, LocalDateTime checkTime);
    void addFood(Food food, LocalDateTime checkTime);
    void removeFood(Food food);
    Set<Food> getFoods();
    FoodStorage getChainStorage();

}
