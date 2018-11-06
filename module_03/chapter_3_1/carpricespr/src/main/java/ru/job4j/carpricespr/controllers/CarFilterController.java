package ru.job4j.carpricespr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import java.util.List;

@Controller
@RequestMapping("/carFilter")
public class CarFilterController {
    private final static Logger LOGGER = LogManager.getLogger(CarFilterController.class);

    private final Logic logic;

    @Autowired
    public CarFilterController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping
    @ResponseBody
    public String applyCarFilter(@RequestParam("brand") Integer brand,
                                 @RequestParam("date") boolean date,
                                 @RequestParam("photo")boolean photo) throws JsonProcessingException, StoreException {
        LOGGER.traceEntry();

        List<Car> cars =  logic.findCars(brand == null ? 0 : brand, date, photo);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(cars);
    }
}