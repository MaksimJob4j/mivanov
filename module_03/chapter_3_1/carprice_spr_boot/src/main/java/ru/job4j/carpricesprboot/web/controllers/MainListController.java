package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.domain.description.Brand;
import ru.job4j.carpricesprboot.service.EntityService;
import ru.job4j.carpricesprboot.service.UserService;

@Controller
@RequestMapping()
public class MainListController {
    private final static Logger LOGGER = LogManager.getLogger(MainListController.class);

    private final UserService userService;
    private final EntityService<Brand> brandService;

    @Autowired
    public MainListController(UserService userService, EntityService<Brand> brandService) {
        this.userService = userService;
        this.brandService = brandService;
    }

    @GetMapping("/list")
    public String redirectToMainList(ModelMap model) {
        LOGGER.traceEntry();
        return "redirect:/";
    }

    @GetMapping("/")
    public String mainList(ModelMap model) {
        LOGGER.traceEntry();
        User loginUser = userService.getLoginUserBySecurityContext(SecurityContextHolder.getContext());
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("brands", brandService.findOrdered());
        return "mainList";
    }
}