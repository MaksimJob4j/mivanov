package ru.job4j.ood.products.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.AbstractFoodStorage;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.FoodStorage;
import ru.job4j.ood.products.Recyclable;

import java.time.LocalDateTime;

public class RecyclingStorage extends AbstractFoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(RecyclingStorage.class);

    public RecyclingStorage(FoodStorage chainStorage) {
        super(chainStorage, 1);
    }

    @Override
    public boolean isFit(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        if (super.isFit(food, checkTime)) {
            try {
                return ((Recyclable) food).canRecycle();
            } catch (ClassCastException e) {
            }
        }
        return false;
    }

}
