package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.carpricespr.items.description.Brand;
import ru.job4j.carpricespr.service.EntityService;

@Controller
@RequestMapping("/")
public class MainListController {
    private final static Logger LOGGER = LogManager.getLogger(MainListController.class);

    private final EntityService<Brand> brandService;

    @Autowired
    public MainListController(EntityService<Brand> brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String mainList(ModelMap model) {
        LOGGER.traceEntry();

        model.addAttribute("brands", brandService.findOrdered());
        return "mainlist";
    }
}