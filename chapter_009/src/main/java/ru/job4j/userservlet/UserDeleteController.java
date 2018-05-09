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

public class UserDeleteController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UserDeleteController.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());

        try {
            users.delete(req.getParameter("id"));
        } catch (UserException e) {
            LOGGER.error("error", e);
        }

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
