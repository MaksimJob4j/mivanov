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
import java.io.PrintWriter;

public class UserUpdateServlet  extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.UserUpdateServlet.class);

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
        StringBuilder table = new StringBuilder("<table>");
        table.append("<form action='" + req.getContextPath() + "/edit' method='post' >"
                + "<tr align='center'><td>Id</td><td>Name</td><td>Login</td><td>Email</td><td>CreateDate</td></tr>"
                + "<tr>"
                + "<td>" + user.getId() + "</td>"
                + "<input name='id' type='hidden' value='" + user.getId() + "'>"
                + "<td><input name='name' value='" + user.getName() + "'</td>"
                + "<td><input name='login' value='" + user.getLogin() + "'</td>"
                + "<td><input name='email' value='" + user.getEmail() + "'</td>"
                + "<td>" + user.getCreateDate() + "</td>"
                + "</tr>"
                + "</table>"
                + "<input type='submit' value='ACCEPT'>"
                + "</form>"
                + "<form action='" + req.getContextPath() + "/list' method='get' >"
                + "<input type='submit' value='CANCEL'>"
                + "</form>");

        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <title>Edit Users</title>"
                + "</head>"
                + "<body>"
                + table
                + "</body>"
                + "</html>");
        writer.flush();
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

        new UserServlet().doGet(req, resp);
    }
}