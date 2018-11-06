package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

@Controller
@RequestMapping(value = "/signin")
@SessionAttributes(names = "loginUser", types = User.class)
public class SignInController {
    private final static Logger LOGGER = LogManager.getLogger(SignInController.class);

    private final Logic logic;

    @Autowired
    public SignInController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping
    public String signIn() {
        LOGGER.traceEntry();
        return "login";
    }

    @PostMapping
    public String signInPost(@RequestParam String login,
                             @RequestParam String password,
                             @RequestParam(required = false) String newUser,
                             ModelMap model) throws StoreException {
        LOGGER.traceEntry();
        User loginUser = logic.findUserByLogin(login);
        if (newUser == null) {
            if (loginUser != null && loginUser.getPassword().equals(password)) {
                model.addAttribute("loginUser", loginUser);
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid sign in!");
                return "login";
            }
        } else {
            if (loginUser == null) {
                loginUser = new User();
                loginUser.setLogin(login);
                loginUser.setPassword(password);
                logic.createUser(loginUser);
                model.addAttribute("loginUser", loginUser);
                return "redirect:/";
            } else {
                model.addAttribute("error", "Login already used!");
                return "login";
            }
        }
    }
}