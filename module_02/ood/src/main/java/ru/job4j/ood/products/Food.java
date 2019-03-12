package ru.job4j.ood.products;

import java.time.LocalDateTime;

public interface Food {

    String getName();
    LocalDateTime getCreateDate();
    LocalDateTime getExpiryDate();
    double getPrice();
    void setPrice(double price);
    double getDiscount();
    void setDiscount(double discount);
    FoodStorage getStorage();
    void setStorage(FoodStorage storage);
    double getLifePercentage(LocalDateTime date);

}
