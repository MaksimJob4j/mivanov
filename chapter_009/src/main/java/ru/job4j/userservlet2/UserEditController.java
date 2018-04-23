package ru.job4j.userservlet2;

import org.apache.log4j.Logger;
import ru.job4j.User;
import ru.job4j.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserEditController extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserEditController.class);
    private final UserStore users = UserStore.INSTANCE;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");

        req.setAttribute("user", users.getUser(req.getParameter("id")));
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Вызван метод");
        resp.setContentType("text/html");

        User user = users.getUser(req.getParameter("id"));
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        users.editUser(user);

        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
