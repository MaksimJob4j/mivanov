package ru.job4j.ood.products;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public abstract class Food {
    private final static Logger LOGGER = LogManager.getLogger(Food.class);
    private final String name;
    private final LocalDateTime createDate;
    private final LocalDateTime expiryDate;
    private double price;
    private double discount;
    private FoodStorage storage;

    protected Food(String name, LocalDateTime createDate, LocalDateTime expiryDate) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
    }


    public double getLifePercentage(LocalDateTime date) {
        LOGGER.traceEntry();
        return (double) ChronoUnit.MINUTES.between(this.createDate, date)
                / ChronoUnit.MINUTES.between(this.createDate, this.expiryDate);
    }

    public void changeStorage(FoodStorage storage) {
        LOGGER.traceEntry();
        if (this.storage != storage) {
            if (this.storage != null) {
                this.storage.removeFood(this);
            }
            this.storage = storage;
            storage.addFood(this);
        }
    }
}
