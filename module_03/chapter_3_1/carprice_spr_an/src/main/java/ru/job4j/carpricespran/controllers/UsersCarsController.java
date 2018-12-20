package ru.job4j.carpricespran.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.carpricespran.items.Car;
import ru.job4j.carpricespran.service.CarService;

import java.security.Principal;
import java.util.List;

@Controller
public class UsersCarsController {
    private final static Logger LOGGER = LogManager.getLogger(UsersCarsController.class);

    private final CarService carService;

    @Autowired
    public UsersCarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/users_cars")
    public String usersCars(ModelMap model,
                            Principal principal) {
        LOGGER.traceEntry();

        List<Car> cars = carService.findByUserLogin(principal.getName());
        model.addAttribute("users_cars", cars);
        return "userscars";
    }
}