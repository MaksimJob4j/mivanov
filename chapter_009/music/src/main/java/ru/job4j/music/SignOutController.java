package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOutController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.SignOutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        req.getSession().invalidate();
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}