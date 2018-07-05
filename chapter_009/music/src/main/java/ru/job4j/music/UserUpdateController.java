package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Address;
import ru.job4j.music.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.UserUpdateController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());

        User user = (User) req.getAttribute("user");

        if (user == null) {
            try {
                user = logic.findUserById(req.getParameter("id"));
                req.setAttribute("user", user);
            } catch (StoreException e) {
                LOGGER.traceEntry();
                req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
                resp.sendRedirect(String.format("%s/user_info?id=%s", req.getContextPath(), user.getId()));

            }
        }

        Address address = (Address) req.getAttribute("address");

        if (address == null) {
            address = user.getAddress();
            if (address == null) {
                address = new Address();
            }
        }
        req.setAttribute("address", address);
        req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User user = (User) req.getAttribute("user");
        Address address = (Address) req.getAttribute("address");
        try {
            if (user == null) {
                user = logic.findUserById(req.getParameter("id"));
            }
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setRole(logic.findRoleByName(req.getParameter("role")));
            if (address == null) {
                address = new Address();
            }
            address.setCountry(req.getParameter("country"));
            address.setCity(req.getParameter("city"));
            address.setRestAddress(req.getParameter("restAddress"));
            if (!"".equals(address.getCountry()) || !"".equals(address.getCity()) || !"".equals(address.getRestAddress())) {
                user.setAddress(address);
            } else {
                user.setAddress(null);
            }
            req.setAttribute("user", user);
            req.setAttribute("address", address);
            logic.update(user);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            req.setAttribute("saveerror", e.getMessage());
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/user_info?id=%s", req.getContextPath(), user.getId()));
    }
}