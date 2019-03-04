package ru.job4j.ood.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.storage.Shop;
import ru.job4j.ood.products.storage.Trash;
import ru.job4j.ood.products.storage.Warehouse;

import java.time.LocalDateTime;

public class ControlQuality {
    private final static Logger LOGGER = LogManager.getLogger(ControlQuality.class);
    private final Warehouse warehouse;
    private final Shop shop;
    private final Trash trash;

    public ControlQuality(Warehouse warehouse, Shop shop, Trash trash) {
        this.warehouse = warehouse;
        this.shop = shop;
        this.trash = trash;
    }

    public void redistributeProducts(Food food, LocalDateTime checkTime, double discount) {
        LOGGER.traceEntry();
        double lifePercentage = food.getLifePercentage(checkTime);
        if (lifePercentage < 0.25) {
            food.changeStorage(this.warehouse);
        } else if (lifePercentage > 1) {
            food.changeStorage(this.trash);
        } else {
            food.changeStorage(this.shop);
            if (lifePercentage > 0.75) {
                food.setDiscount(discount);
            }
        }
    }
}
