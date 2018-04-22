package ru.job4j.userservlet;

import org.apache.log4j.Logger;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateUser extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.userservlet.CreateUser.class);
    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
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
                + "    <title>List Users</title>"
                + "</head>"
                + "<body>"
                + table
                + "</body>"
                + "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        users.addUser(
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email"));
        ListUsers listUsers = new ListUsers();
        listUsers.doGet(req, resp);
    }

}
