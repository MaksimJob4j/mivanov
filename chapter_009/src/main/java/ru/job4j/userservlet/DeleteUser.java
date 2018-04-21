package ru.job4j.userservlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUser extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.userservlet.DeleteUser.class);

    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        users.deleteUser(req.getParameter("id"));
        ListUsers listUsers = new ListUsers();
        listUsers.doGet(req, resp);
    }
}
