package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.UserCreateController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        List<Role> roleList = null;
        if (loginUser.getRole().getLevel() == 10) {
            try {
                roleList = logic.findAllRoles();
            } catch (StoreException e) {
                LOGGER.traceEntry();
                req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
            }
        }
        req.setAttribute("roles", roleList);
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User user = (User) req.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setAddress(new Address());
        }
        try {
            String login = req.getParameter("login");
            if (login == null) {
                throw new StoreException("Login is NULL");
            } else {
                user.setLogin(login);
            }
            String password = req.getParameter("password");
            if (password == null) {
                throw new StoreException("Password is NULL");
            } else {
                user.setPassword(password);
            }
            String role = req.getParameter("role");
            if (role == null) {
                throw new StoreException("Role is NULL");
            } else {
                user.setRole(logic.findRoleByName(role));
            }
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
            logic.add(user);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
            req.setAttribute("user", user);
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}