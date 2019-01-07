package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.carpricesprboot.web.editors.*;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.domain.description.*;
import ru.job4j.carpricesprboot.service.CarPartsService;
import ru.job4j.carpricesprboot.service.CarService;
import ru.job4j.carpricesprboot.service.PhotoService;
import ru.job4j.carpricesprboot.service.UserService;

import java.io.IOException;

@Controller
public class CarCreateController {
    private final static Logger LOGGER = LogManager.getLogger(CarCreateController.class);
    private final CarPartsService carPartsService;
    private final CarService carService;
    private final UserService userService;
    private final PhotoService photoService;

    @Autowired
    public CarCreateController(CarPartsService carPartsService,
                               CarService carService,
                               UserService userService,
                               PhotoService photoService) {

        this.carPartsService = carPartsService;
        this.carService = carService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @GetMapping("/newCar")
    public String createCar(ModelMap model) {
        LOGGER.traceEntry();
        model.addAttribute("carPats", carPartsService.pickParts());
        return "createCar";
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

    @PostMapping("/newCar")
    public String createCar(@Validated Car car,
                            @RequestParam("photofile") MultipartFile file) throws IOException {
        LOGGER.traceEntry();

        User loginUser = userService.getLoginUserBySecurityContext(SecurityContextHolder.getContext());
        car.setOwner(loginUser);
        carService.create(car);
        if (!file.isEmpty()) {
            Photo photo = new Photo();
            photo.setFileName(file.getOriginalFilename());
            photo.setFileData(file.getBytes());
            photo.setCar(car);
            photoService.create(photo);
        }
        return "redirect:/usersCars";
    }
}