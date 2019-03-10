package ru.job4j.ood.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class ControlQuality {
    private final static Logger LOGGER = LogManager.getLogger(ControlQuality.class);
    private final FoodStorage foodStorage;

    public ControlQuality(FoodStorage foodStorage) {
        this.foodStorage = foodStorage;
    }

    public void redistributeProducts(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        this.foodStorage.addFood(food, checkTime);
    }
}
