package ru.job4j.ood.products;

import java.time.LocalDateTime;

public abstract class FoodDecorator implements Food {
    private final Food food;

    public FoodDecorator(Food food) {
        this.food = food;
    }

    @Override
    public String getName() {
        return this.food.getName();
    }

    @Override
    public LocalDateTime getCreateDate() {
        return this.food.getCreateDate();
    }

    @Override
    public LocalDateTime getExpiryDate() {
        return this.food.getExpiryDate();
    }

    @Override
    public double getPrice() {
        return this.food.getPrice();
    }

    @Override
    public void setPrice(double price) {
        this.food.setPrice(price);
    }

    @Override
    public double getDiscount() {
        return this.food.getDiscount();
    }

    @Override
    public void setDiscount(double discount) {
        this.food.setDiscount(discount);
    }

    @Override
    public FoodStorage getStorage() {
        return this.food.getStorage();
    }

    @Override
    public void setStorage(FoodStorage storage) {
        this.food.setStorage(storage);
    }

    @Override
    public double getLifePercentage(LocalDateTime date) {
        return this.food.getLifePercentage(date);
    }
}
