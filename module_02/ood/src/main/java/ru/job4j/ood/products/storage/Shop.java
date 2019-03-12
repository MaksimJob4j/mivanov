package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.AbstractFoodStorage;
import ru.job4j.ood.products.FoodStorage;

import java.time.LocalDateTime;

public class Shop extends AbstractFoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Shop.class);
    private final double discountLifeCriteria;
    private final double finalDiscount;

    public Shop(FoodStorage foodStorage, double minLifeCriteria, double discountLifeCriteria, double finalDiscount) {
        super(foodStorage, minLifeCriteria, 1);
        this.discountLifeCriteria = discountLifeCriteria;
        this.finalDiscount = finalDiscount;
    }

    @Override
    public void addFood(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        super.addFood(food, checkTime);
        setDiscount(food, checkTime);
    }

    public void setDiscount(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        if (this.getFoods().contains(food)
                && food.getLifePercentage(checkTime) > this.discountLifeCriteria) {
            food.setDiscount(this.finalDiscount);
        }
    }
}
