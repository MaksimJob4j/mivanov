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
import ru.job4j.carpricespr.items.CarParts;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.items.description.*;
import ru.job4j.carpricespr.service.CarService;
import ru.job4j.carpricespr.service.PhotoService;
import ru.job4j.carpricespr.service.impl.CarPartsServiceImpl;

import java.io.IOException;

@Controller
@SessionAttributes(names = "loginUser", types = User.class)
public class CarCreateController {
    private final static Logger LOGGER = LogManager.getLogger(CarCreateController.class);
    private final CarPartsServiceImpl carPartsService;
    private final CarService carService;
    private final PhotoService photoService;


    @Autowired
    public CarCreateController(CarPartsServiceImpl carPartsService,
                               CarService carService,
                               PhotoService photoService) {

        this.carPartsService = carPartsService;
        this.carService = carService;
        this.photoService = photoService;
    }

    @GetMapping("/newcar")
    public String createCar(ModelMap model) {
        LOGGER.traceEntry();
        CarParts carParts = carPartsService.pickParts();
        model.addAttribute("bodies", carParts.getBodies());
        model.addAttribute("brands", carParts.getBrands());
        model.addAttribute("colors", carParts.getColors());
        model.addAttribute("drives", carParts.getDrives());
        model.addAttribute("engines", carParts.getEngines());
        model.addAttribute("transmissions", carParts.getTransmissions());
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
                            @RequestParam("photofile") MultipartFile file) throws IOException {
        LOGGER.traceEntry();

        car.setOwner(loginUser);
        carService.create(car);
        if (!file.isEmpty()) {
            Photo photo = new Photo();
            photo.setFileName(file.getOriginalFilename());
            photo.setFileData(file.getBytes());
            photo.setCar(car);
            photoService.create(photo);
        }
        return "redirect:users_cars";
    }
}