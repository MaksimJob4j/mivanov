package ru.job4j.music.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.MusicLogic;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Address;
import ru.job4j.music.entities.Role;
import ru.job4j.music.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCreateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UserCreateController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        List<Role> roleList = null;
        try {
            if (loginUser.getRole().getLevel() == 10) {
                roleList = logic.findAllRoles();
            }
            req.setAttribute("roles", roleList);
            req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        try {
            User user = (User) req.getAttribute("user");
            if (user == null) {
                user = new User();
                user.setAddress(new Address());
            }
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setRole(logic.findRoleByName(req.getParameter("role")));
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String restAddress = req.getParameter("restAddress");
            if (!"".equals(country) || !"".equals(city) || !"".equals(restAddress)) {
                Address address = new Address();
                address.setCountry(country);
                address.setCity(city);
                address.setRestAddress(restAddress);
                user.setAddress(address);
            } else {
                user.setAddress(null);
            }

            req.setAttribute("user", user);
            if (user.getLogin() == null || "".equals(user.getLogin())) {
                req.setAttribute("error", "Enter login!");
                doGet(req, resp);
            } else if (user.getPassword() == null || "".equals(user.getPassword())) {
                req.setAttribute("error", "Enter password!");
                doGet(req, resp);
            } else if (user.getRole() == null) {
                req.setAttribute("error", "Enter role!");
                doGet(req, resp);
            } else if (logic.findUserByLogin(user.getLogin()) != null) {
                req.setAttribute("error", String.format("Login %s is already in the database!", user.getLogin()));
                doGet(req, resp);
            } else {
                logic.add(user);
            }
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}