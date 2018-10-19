package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeSoldController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ChangeSoldController.class);
    private final Logic logic = Logic.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();

        try {
            String carId = req.getParameter("car_id");
            Boolean sold = Boolean.parseBoolean(req.getParameter("sold"));
            Car car = logic.findCar(carId);
            car.setSold(sold);
            logic.update(car);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}