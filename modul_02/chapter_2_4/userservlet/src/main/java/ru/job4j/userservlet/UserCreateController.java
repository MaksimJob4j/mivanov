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

public class UserCreateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.UserCreateController.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());
        List<Role> roleList = null;
        try {
            roleList = users.findAllRoles();
        } catch (UserException e) {
            LOGGER.error("error", e);
        }
        req.setAttribute("roles", roleList);
        req.setAttribute("countries", new CityHandler().getCountries());
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setRole(req.getParameter("role"));
        user.setCountry(req.getParameter("country"));
        user.setCity(req.getParameter("city"));

        try {
            users.add(user);
        } catch (UserException e) {
            LOGGER.error("error", e);
            req.setAttribute("error", e.getMessage());
            req.setAttribute("user", user);
            doGet(req, resp);
        }

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}