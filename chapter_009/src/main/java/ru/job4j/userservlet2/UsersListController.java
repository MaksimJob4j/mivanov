package ru.job4j.userservlet2;

import org.apache.log4j.Logger;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersListController extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UsersListController.class);
    private final UserStore users = UserStore.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");

        req.setAttribute("users", users.getUsers());

        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);


    }
}
