package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.service.RoleService;
import ru.job4j.carpricespr.service.UserService;

@Controller
@RequestMapping(value = "/createUser")
public class UserCreateController {
    private final static Logger LOGGER = LogManager.getLogger(UserCreateController.class);

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserCreateController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String signIn() {
        LOGGER.traceEntry();
        return "createUser";
    }

    @PostMapping
    public String signInPost(@RequestParam String login,
                             @RequestParam String password,
                             ModelMap model) {
        LOGGER.traceEntry();
        User newUser = userService.findByLogin(login);
        if (newUser == null) {
            newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(roleService.findByName("ROLE_USER"));
            userService.create(newUser);
            model.addAttribute("msg", "User created successful!");
            return "createUser";
        } else {
            model.addAttribute("error", String.format("Login %s already used!", login));
            return "createUser";
        }
    }
}