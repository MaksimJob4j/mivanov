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
        LOGGER.traceEntry();

        try {
            User user = (User) req.getAttribute("user");
            if (user == null) {
                user = logic.findUserById(req.getParameter("id"));
                req.setAttribute("user", user);
            }
            Address address = (Address) req.getAttribute("address");
            if (address == null) {
                address = user.getAddress();
                if (address == null) {
                    address = new Address();
                }
            }
            req.setAttribute("address", address);
            req.setAttribute("roles", logic.findAllRoles());
            req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
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
            Address address = (Address) req.getAttribute("address");
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
            if (user.getLogin() == null || "".equals(user.getLogin())) {
                req.setAttribute("error", "Enter login!");
                doGet(req, resp);
            } else if (user.getPassword() == null || "".equals(user.getPassword())) {
                req.setAttribute("error", "Enter password!");
                doGet(req, resp);
            } else if (user.getRole() == null) {
                req.setAttribute("error", "Enter role!");
                doGet(req, resp);
            } else {
                User baseUser = logic.findUserByLogin(user.getLogin());
                if (baseUser != null && baseUser.getId() != user.getId()) {
                    req.setAttribute("error", String.format("Login %s is already in the database!", user.getLogin()));
                    doGet(req, resp);
                } else {
                    logic.update(user);
                    resp.sendRedirect(String.format("%s/user_info?id=%s", req.getContextPath(), user.getId()));
                }
            }
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}