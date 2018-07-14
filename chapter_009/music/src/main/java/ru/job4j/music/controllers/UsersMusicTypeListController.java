package ru.job4j.music.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMusicTypeListController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UsersMusicTypeListController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        int userId = Integer.parseInt(req.getParameter("user-id"));
        new JSONHandler().makeMusicTypeJSONResponse(req, resp, userId);
    }
}