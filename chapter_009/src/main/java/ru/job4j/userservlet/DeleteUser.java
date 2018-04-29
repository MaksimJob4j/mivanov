package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUser extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.DeleteUser.class);

    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry("Query: " + req.getQueryString());
        users.deleteUser(req.getParameter("id"));
        ListUsers listUsers = new ListUsers();
        listUsers.doGet(req, resp);
    }
}
