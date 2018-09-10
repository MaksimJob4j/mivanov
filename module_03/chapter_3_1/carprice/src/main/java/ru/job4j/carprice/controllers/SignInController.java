package ru.job4j.carprice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.controllers.SignInController.class);

    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User loginUser = logic.findUserByLogin(login);
            if (req.getParameter("new_user") == null) {
                if (loginUser != null && loginUser.getPassword().equals(password)) {
                    this.setLoginUser(req, resp, loginUser);
                } else {
                    req.setAttribute("error", "Invalid sign in!");
                    doGet(req, resp);
                }
            } else {
                if (loginUser == null) {
                    loginUser = new User();
                    loginUser.setLogin(login);
                    loginUser.setPassword(password);
                    logic.createUser(loginUser);
                    this.setLoginUser(req, resp, loginUser);
                } else {
                    req.setAttribute("error", "Login already used!");
                    doGet(req, resp);
                }
            }
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }

    private void setLoginUser(HttpServletRequest req, HttpServletResponse resp, User loginUser) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute("loginUser", loginUser);
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}