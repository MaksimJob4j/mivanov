package ru.job4j.ood.products.storage;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.Food;
import ru.job4j.ood.products.FoodStorage;

import java.time.LocalDateTime;

@Getter
@Setter
public class Shop extends FoodStorage {
    private final static Logger LOGGER = LogManager.getLogger(Shop.class);
    private double discountLifeCriteria;
    private double finalDiscount;

    public Shop(Trash trash, double lifeCriteria, double discountLifeCriteria, double finalDiscount) {
        super(trash, lifeCriteria);
        this.discountLifeCriteria = discountLifeCriteria;
        this.finalDiscount = finalDiscount;
    }

    @Override
    public void addFood(Food food, LocalDateTime checkTime) {
        super.addFood(food, checkTime);
        setDiscount(food, checkTime);
    }

    public void setDiscount(Food food, LocalDateTime checkTime) {
        if (food.getStorage() == this
                && food.getLifePercentage(checkTime) > this.discountLifeCriteria) {
            food.setDiscount(this.finalDiscount);
        }
    }
}
