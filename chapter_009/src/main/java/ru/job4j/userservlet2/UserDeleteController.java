package ru.job4j.userservlet2;

import org.apache.log4j.Logger;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDeleteController extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserDeleteController.class);

    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        resp.setContentType("text/html");

        users.deleteUser(req.getParameter("id"));

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
