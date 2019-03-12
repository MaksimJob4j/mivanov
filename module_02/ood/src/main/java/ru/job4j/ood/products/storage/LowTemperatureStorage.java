package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.FoodStorage;
import ru.job4j.ood.products.LowTemperature;

import java.time.LocalDateTime;

public class LowTemperatureStorage extends FoodStorageDecorator {
    private final static Logger LOGGER = LogManager.getLogger(LowTemperatureStorage.class);
    private final boolean lowTemperature;

    public LowTemperatureStorage(FoodStorage foodStorage, boolean lowTemperature) {
        super(foodStorage);
        this.lowTemperature = lowTemperature;
    }

    @Override
    public boolean isFit(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        if (super.isFit(food, checkTime)) {
            try {
                return  ((LowTemperature) food).needLowTemperature() == this.lowTemperature;
            } catch (ClassCastException e) {
                return true;
            }
        } else {
            return false;
        }
    }
}
