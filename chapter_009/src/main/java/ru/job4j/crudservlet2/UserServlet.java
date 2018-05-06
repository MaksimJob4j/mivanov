package ru.job4j.crudservlet2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class UserServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.crudservlet.CrudServlet.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        LOGGER.debug("Query: " + req.getQueryString());
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> usersList = users.findAll();
        for (User user : usersList) {
            writer.append(user.toString());
        }
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        String action = req.getParameter("action").toUpperCase(Locale.ENGLISH);
        try {
            writer.append(new ActionDispatcher().init(req, users).execute(() -> Action.Type.valueOf(action)));
        } catch (IllegalArgumentException e) {
            writer.append("Illegal action");
        }
        writer.flush();
    }
}



