package ru.job4j.ood.products;

public class RecyclableFood extends FoodDecorator implements Recyclable {

    public RecyclableFood(Food food) {
        super(food);
    }

}
