package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.job4j.carpricesprboot.domain.description.Model;
import ru.job4j.carpricesprboot.service.ModelService;

import java.util.List;

@Controller
@RequestMapping("/models")
public class ModelListController {
    private final static Logger LOGGER = LogManager.getLogger(ModelListController.class);

    private final ModelService modelService;

    @Autowired
    public ModelListController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Model> models(@RequestParam("brand") String brandId) {
        LOGGER.traceEntry();

        return modelService.findByBrandId(Integer.parseInt(brandId));
    }
}