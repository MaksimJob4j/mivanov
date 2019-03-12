package ru.job4j.ood.products;

public class LowTemperatureFood extends FoodDecorator implements LowTemperature {

    public LowTemperatureFood(Food food) {
        super(food);
    }

}
