package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/signout")
public class SignOutController {
    private final static Logger LOGGER = LogManager.getLogger(SignOutController.class);

    @GetMapping
    protected String signout(SessionStatus status) {
        LOGGER.traceEntry();
        status.setComplete();
        return  "redirect:/";
    }
}