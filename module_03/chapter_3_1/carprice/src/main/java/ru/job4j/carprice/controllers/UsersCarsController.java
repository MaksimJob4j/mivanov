package ru.job4j.carprice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class UsersCarsController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.controllers.UsersCarsController.class);

    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
        } else {
            try {
                List<Car> cars = logic.findUsersCars(loginUser.getId());
                cars.sort(Comparator.comparing(Car::getDateCreated).reversed());
                req.setAttribute("users_cars", cars);
                req.getRequestDispatcher("/WEB-INF/views/userscars.jsp").forward(req, resp);
            } catch (StoreException e) {
                LOGGER.error("error", e);
                resp.sendError(500, e.getMessage());
            }
        }
    }
}