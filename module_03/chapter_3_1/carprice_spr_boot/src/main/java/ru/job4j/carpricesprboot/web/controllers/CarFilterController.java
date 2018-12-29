package ru.job4j.carpricesprboot.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.service.CarService;

import java.util.List;

@Controller
@RequestMapping("/carFilter")
public class CarFilterController {
    private final static Logger LOGGER = LogManager.getLogger(CarFilterController.class);

    private final CarService carService;

    @Autowired
    public CarFilterController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @ResponseBody
    public String applyCarFilter(@RequestParam("brand") Integer brandId,
                                 @RequestParam("date") boolean dateFilter,
                                 @RequestParam("photo")boolean photoFilter) throws JsonProcessingException {
        LOGGER.traceEntry();

        List<Car> cars =  carService.findCars(brandId == null ? 0 : brandId, dateFilter, photoFilter);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(cars);
    }
}