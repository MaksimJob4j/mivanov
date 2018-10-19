package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CarInfoController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(CarInfoController.class);

    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        try {
            Car car = logic.findCar(req.getParameter("id"));
            if (car == null) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            }
            req.setAttribute("car_info", car);
            req.getRequestDispatcher("/WEB-INF/views/car.jsp").forward(req, resp);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}