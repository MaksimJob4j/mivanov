package ru.job4j.crudservlet2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.ListUsers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserCreateServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.crudservlet2.UserCreateServlet.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());
        StringBuilder table = new StringBuilder("<table>");
        table.append("<form action='" + req.getContextPath() + "/create' method='post' >"
                + "<tr align='center'><td>Name</td><td>Login</td><td>Email</td></tr>"
                + "<tr>"
                + "<td><input name='name' value=''</td>"
                + "<td><input name='login' value=''</td>"
                + "<td><input name='email' value=''</td>"
                + "</tr>"
                + "</table>"
                + "<input type='submit' value='CREATE'>"
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
                + "    <title>Create User</title>"
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
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        try {
            users.add(user);
        } catch (UserException e) {
            LOGGER.error("error", e);
        }
        new UserServlet().doGet(req, resp);
    }
}
