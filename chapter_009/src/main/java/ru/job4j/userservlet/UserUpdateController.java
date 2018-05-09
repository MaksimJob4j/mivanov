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

public class UserUpdateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UserUpdateController.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());

        User user = null;
        try {
            user = users.findById(req.getParameter("id"));
        } catch (UserException e) {
            LOGGER.error("error", e);
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        User user = new User();
        user.setId(req.getParameter("id"));
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        try {
            users.update(user);
        } catch (UserException e) {
            LOGGER.error("error", e);
        }

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}