package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/signin")
public class SignInController {
    private final static Logger LOGGER = LogManager.getLogger(SignInController.class);

    @GetMapping
    public String signIn(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            ModelMap modelMap) {
        LOGGER.traceEntry();

        if (error != null) {
            modelMap.addAttribute("error", "Invalid username and password!");
        }
        if (logout != null) {
            modelMap.addAttribute("msg", "You've been logged out successfully.");
        }
        return "login";
    }
}