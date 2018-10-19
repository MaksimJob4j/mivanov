package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.description.Brand;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainListController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(MainListController.class);

    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        try {
            List<Brand> brands = logic.findBrands();
            req.setAttribute("brands", brands);
            req.getRequestDispatcher("/WEB-INF/views/mainlist.jsp").forward(req, resp);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}