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
import java.util.*;

public class UserListController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UserListController.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        LOGGER.debug("Query: " + req.getQueryString());

        List<User> usersList = null;
        try {
            usersList = users.findAll();
        } catch (UserException e) {
            LOGGER.error("error", e);
        }
        req.setAttribute("users", usersList);
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        LOGGER.traceEntry();
//
//        resp.setContentType("text/html");
//        PrintWriter writer = new PrintWriter(resp.getOutputStream());
//
//        String action = req.getParameter("action").toUpperCase(Locale.ENGLISH);
//        try {
//            writer.append(new ActionDispatcher().init(req, users).execute(() -> Action.Type.valueOf(action)));
//        } catch (IllegalArgumentException e) {
//            writer.append("Illegal action");
//        }
//        writer.flush();
//    }
}