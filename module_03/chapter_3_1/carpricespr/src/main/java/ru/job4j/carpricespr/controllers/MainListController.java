package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

@Controller
@RequestMapping("/")
public class MainListController {
    private final static Logger LOGGER = LogManager.getLogger(MainListController.class);

    private final Logic logic;

    @Autowired
    public MainListController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping
    public String mainList(ModelMap model) throws StoreException {
        LOGGER.traceEntry();

        model.addAttribute("brands", logic.findBrands());
        return "mainlist";
    }
}