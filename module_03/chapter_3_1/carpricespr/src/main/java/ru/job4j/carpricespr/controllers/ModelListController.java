package ru.job4j.carpricespr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carpricespr.dao.StoreException;
import ru.job4j.carpricespr.items.description.Model;
import ru.job4j.carpricespr.Logic;

import java.util.List;

@Controller
@RequestMapping("/models")
public class ModelListController {
    private final static Logger LOGGER = LogManager.getLogger(ModelListController.class);
    private final Logic logic;

    @Autowired
    public ModelListController(Logic logic) {
        this.logic = logic;
    }

    @GetMapping
    @ResponseBody
    public String models(@RequestParam("brand") String brand) throws StoreException, JsonProcessingException {
        LOGGER.traceEntry();

        String result = "";
        List<Model> models = logic.findModelsByBrand(brand);
        if (models != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            result = mapper.writeValueAsString(models);
        }
        return result;
    }
}