package ru.job4j.carpricespr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarFilterController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(CarFilterController.class);

    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();

        String brandIdString = req.getParameter("brand");
        int brandFilter = ("".equals(brandIdString) || brandIdString == null) ? 0 : Integer.parseInt(brandIdString);
        boolean dateFilter = Boolean.parseBoolean(req.getParameter("date"));
        boolean photoFilter = Boolean.parseBoolean(req.getParameter("photo"));

        try {
            List<Car> cars = logic.findCars(brandFilter, dateFilter, photoFilter);
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.writeValue(writer, cars);
            writer.flush();
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}