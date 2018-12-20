package ru.job4j.carpricespran.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.job4j.carpricespran.items.description.Model;
import ru.job4j.carpricespran.service.ModelService;

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

    @GetMapping
    @ResponseBody
    public String models(@RequestParam("brand") String brandId) throws JsonProcessingException {
        LOGGER.traceEntry();

        String result = "";
        List<Model> models = modelService.findByBrandId(Integer.parseInt(brandId));
        if (models.size() > 0) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            result = mapper.writeValueAsString(models);
        }
        return result;
    }
}