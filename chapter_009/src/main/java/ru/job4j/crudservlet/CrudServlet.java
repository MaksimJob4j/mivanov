package ru.job4j.crudservlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CrudServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.crudservlet.CrudServlet.class);

    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> usersList = users.getUsers(req.getParameterMap());
        for (User user: usersList) {
            writer.append(user.toString());
        }
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.add(req.getParameterMap());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.edit(req.getParameterMap());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.delete(req.getParameterMap());
    }
}

