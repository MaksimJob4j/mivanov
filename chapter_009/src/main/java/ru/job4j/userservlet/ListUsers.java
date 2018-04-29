package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.User;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListUsers extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.ListUsers.class);
    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());
        List<User> usersList = users.getUsers();
        StringBuilder table = new StringBuilder();
        if (usersList.size() > 0) {
            table.append("<table><tr align='center'><td>Id</td><td>Name</td><td>Login</td><td>Email</td><td>CreateDate</td></tr>");
            for (User user : usersList) {
                table.append("<tr>"
                        + "<td>" + user.getId() + "</td>"
                        + "<td>" + user.getName() + "</td>"
                        + "<td>" + user.getLogin() + "</td>"
                        + "<td>" + user.getEmail() + "</td>"
                        + "<td>" + user.getCreateDate() + "</td>"
                        + "<td> <form action='" + req.getContextPath() + "/edit' method='get' >"
                        + "<input name='id' type='hidden' value='" + user.getId() + "'>"
                        + "<input type='submit' value='EDIT'>"
                        + "</form></td>"
                        + "<td> <form action='" + req.getContextPath() + "/delete' method='get' >"
                        + "<input name='id' type='hidden' value='" + user.getId() + "'>"
                        + "<input type='submit' value='DELETE'>"
                        + "</form></td>"
                        + "</tr>");
            }
            table.append("</table>");
        } else {
            table.append("<p>There's nothing here!</p>");
        }
        table.append("<br>"
                        + "<form action='" + req.getContextPath() + "/create' method='get' >"
                        + "<input type='submit' value='CREATE'>"
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
}
