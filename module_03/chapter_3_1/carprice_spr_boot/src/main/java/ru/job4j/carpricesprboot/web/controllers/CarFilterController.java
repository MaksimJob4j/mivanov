package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.service.CarService;

import java.util.List;

@Controller
@RequestMapping("/carFilter")
public class CarFilterController {
    private final static Logger LOGGER = LogManager.getLogger(CarFilterController.class);

    private final CarService carService;

    @Autowired
    public CarFilterController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Car> applyCarFilter(@RequestParam("brand") Integer brandId,
                                 @RequestParam("date") boolean dateFilter,
                                 @RequestParam("photo")boolean photoFilter) {
        LOGGER.traceEntry();

        return  carService.findCars(brandId == null ? 0 : brandId, dateFilter, photoFilter);
    }
}