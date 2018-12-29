package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.repository.exceptions.NoCarException;
import ru.job4j.carpricesprboot.service.CarService;

import java.security.Principal;

@Controller
@RequestMapping("/changeSold")
public class ChangeSoldController {
    private final static Logger LOGGER = LogManager.getLogger(ChangeSoldController.class);
    private final CarService carService;

    @Autowired
    public ChangeSoldController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void changeSold(@RequestParam("car_id") int carId,
                           @RequestParam("sold") boolean sold,
                           Principal principal) throws NoCarException {
        LOGGER.traceEntry();

        Car car = carService.findNotNull(carId);
        if (car.getOwner().getLogin().equals(principal.getName())) {
            car.setSold(sold);
            carService.update(car);
        }
    }
}