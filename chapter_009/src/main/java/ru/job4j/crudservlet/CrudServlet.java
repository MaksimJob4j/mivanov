package ru.job4j.crudservlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.crudservlet.CrudServlet.class);

    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> usersList = users.getUsers(getPar(req));
        for (User user: usersList) {
            writer.append(user.toString());
        }
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.add(getPar(req));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.edit(getPar(req));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("Query: " + req.getQueryString());
        users.delete(getPar(req));
    }

    private Map<String, String> getPar(HttpServletRequest request) {
        LOGGER.debug("Вызван метод");
        Map<String, String> result = new HashMap<>();
        String query = request.getQueryString();
        LOGGER.debug("Query: " + query);
        if (query != null) {
            String name = "";
            Boolean trueName = false;
            int start = 0;
            for (int i = 0; i < query.length(); i++) {
                if (query.charAt(i) == '=') {
                    name = query.substring(start, i);
                    if (User.FIELDS.contains(name)) {
                        trueName = true;
                    }
                    start = i + 1;
                } else if (query.charAt(i) == '&' || i == query.length() - 1) {
                    if (trueName) {
                        result.put(name, request.getParameter(name));
                        LOGGER.debug("Параметр: " + name + " = " + result.get(name));
                        trueName = false;
                    }
                    start = i + 1;
                }
            }
        }
        return result;
    }
}

