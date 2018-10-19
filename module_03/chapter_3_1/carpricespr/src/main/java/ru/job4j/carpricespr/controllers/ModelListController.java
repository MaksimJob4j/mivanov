package ru.job4j.carpricespr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.description.Model;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class ModelListController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ModelListController.class);
    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();
        resp.setContentType("text/json");

        try {
            List<Model> models = logic.findModelsByBrand(req.getParameter("brand"));
            if (models != null) {
                models.sort(Comparator.comparing(Model::getName));
                PrintWriter writer = new PrintWriter(resp.getOutputStream());
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(writer, models);
                writer.flush();
            }
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}