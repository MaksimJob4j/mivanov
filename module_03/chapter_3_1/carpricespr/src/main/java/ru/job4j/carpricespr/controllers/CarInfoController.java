package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

@Controller
@RequestMapping("/car")
public class CarInfoController {
    private final static Logger LOGGER = LogManager.getLogger(CarInfoController.class);

    private final Logic logic;

    @Autowired
    public CarInfoController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping
    public String usersCars(@RequestParam("id") int id,
                            ModelMap model) throws StoreException {
        LOGGER.traceEntry();

        Car car = logic.findCar(id);
        if (car == null) {
            return "redirect:/";
        }
        model.addAttribute("car_info", car);
        return "car";
    }
}