package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

@Controller
@RequestMapping("/changeSold")
public class ChangeSoldController {
    private final static Logger LOGGER = LogManager.getLogger(ChangeSoldController.class);
    private final Logic logic;

    @Autowired
    public ChangeSoldController(Logic logic) {
        this.logic = logic;
    }

    @PostMapping
    public void changeSold(@RequestParam("car_id") int carId,
                           @RequestParam("sold") boolean sold) throws StoreException {
        LOGGER.traceEntry();

        Car car = logic.findCar(carId);
        car.setSold(sold);
        logic.update(car);
    }
}