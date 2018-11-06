package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.carpricespr.editors.*;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.items.description.*;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import java.io.*;

@Controller
@SessionAttributes(names = "loginUser", types = User.class)
public class CarCreateController {
    private final static Logger LOGGER = LogManager.getLogger(CarCreateController.class);
    private final Logic logic;

    @Autowired
    public CarCreateController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping("/newcar")
    public String createCar(ModelMap model) throws StoreException {
        LOGGER.traceEntry();

        model.addAttribute("brands", logic.findBrands());
        model.addAttribute("bodies", logic.findBodies());
        model.addAttribute("colors", logic.findColors());
        model.addAttribute("transmissions", logic.findTransmissions());
        model.addAttribute("engines", logic.findEngines());
        model.addAttribute("drives", logic.findDrives());
        return "createcar";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Body.class, new BodyEditor());
        binder.registerCustomEditor(Color.class, new ColorEditor());
        binder.registerCustomEditor(Model.class, new ModelEditor());
        binder.registerCustomEditor(Transmission.class, new TransmissionEditor());
        binder.registerCustomEditor(Engine.class, new EngineEditor());
        binder.registerCustomEditor(Drive.class, new DriveEditor());
    }

    @PostMapping("/newcar")
    public String createCar(@Validated Car car,
                            @ModelAttribute("loginUser") User loginUser,
                            @RequestParam("photofile") MultipartFile file) throws StoreException, IOException {
        LOGGER.traceEntry();

        car.setOwner(loginUser);
        logic.createCar(car);
        if (!file.isEmpty()) {
            Photo photo = new Photo();
            photo.setFileName(file.getOriginalFilename());
            photo.setFileData(file.getBytes());
            photo.setCar(car);
            logic.createPhoto(photo);
        }
        return "redirect:users_cars";
    }
}