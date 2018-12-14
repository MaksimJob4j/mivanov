package ru.job4j.carpricespran.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carpricespran.exceptions.NoCarException;
import ru.job4j.carpricespran.items.Car;
import ru.job4j.carpricespran.service.CarService;

@Controller
@RequestMapping("/car")
public class CarInfoController {
    private final static Logger LOGGER = LogManager.getLogger(CarInfoController.class);

    private final CarService carService;

    @Autowired
    public CarInfoController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String usersCars(@RequestParam("id") int id,
                            ModelMap model) throws NoCarException {
        LOGGER.traceEntry();

        Car car = carService.findNotNull(id);
        model.addAttribute("car_info", car);
        return "car";
    }
}