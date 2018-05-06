package ru.job4j.crudservlet2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDeleteServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.crudservlet2.UserDeleteServlet.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());
        try {
            users.delete(req.getParameter("id"));
        } catch (UserException e) {
            LOGGER.error("error", e);
        }
        new UserServlet().doGet(req, resp);
    }
}
