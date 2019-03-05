package ru.job4j.ood.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.ood.products.storage.Warehouse;

import java.time.LocalDateTime;

public class ControlQuality {
    private final static Logger LOGGER = LogManager.getLogger(ControlQuality.class);
    private final Warehouse warehouse;

    public ControlQuality(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void redistributeProducts(Food food, LocalDateTime checkTime) {
        LOGGER.traceEntry();
        this.warehouse.addFood(food, checkTime);
    }
}
