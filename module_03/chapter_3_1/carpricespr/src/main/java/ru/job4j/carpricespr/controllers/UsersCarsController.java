package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import java.util.List;

@Controller
@SessionAttributes(names = "loginUser", types = User.class)
public class UsersCarsController {
    private final static Logger LOGGER = LogManager.getLogger(UsersCarsController.class);

    private final Logic logic;

    @Autowired
    public UsersCarsController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping("/users_cars")
    public String usersCars(ModelMap model,
                           @ModelAttribute("loginUser") User loginUser) throws StoreException {
        LOGGER.traceEntry();

        if (loginUser == null) {
            return "redirect:/signin";
        } else {
            List<Car> cars = logic.findUsersCars(loginUser.getId());
            model.addAttribute("users_cars", cars);
            return "userscars";
        }
    }
}