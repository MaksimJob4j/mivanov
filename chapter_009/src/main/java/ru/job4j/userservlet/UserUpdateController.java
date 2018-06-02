package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserUpdateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.UserUpdateController.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());

        User loginUser = null;
        try {
            loginUser = users.findByLogin(req.getSession().getAttribute("login").toString());
        } catch (UserException e) {
            e.printStackTrace();
        }
        req.setAttribute("loginUser", loginUser);
        User user = (User) req.getAttribute("user");
        if (user == null) {
            try {
                user = users.findById(req.getParameter("id"));
            } catch (UserException e) {
                LOGGER.error("error", e);
            }
        }
        req.setAttribute("user", user);
        if (loginUser.getRole().equals("admin")) {
            List<Role> roleList = null;
            try {
                roleList = users.findAllRoles();
            } catch (UserException e) {
                LOGGER.error("error", e);
            }
            req.setAttribute("roles", roleList);
        }
        req.setAttribute("countries", new CityHandler().getCountries());
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User user = new User();
        user.setId(req.getParameter("id"));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setRole(req.getParameter("role"));
        user.setCountry(req.getParameter("country"));
        user.setCity(req.getParameter("city"));
        try {
            users.update(user);
        } catch (UserException e) {
            LOGGER.error("error", e);
            req.setAttribute("error", e.getMessage());
            req.setAttribute("user", user);
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}