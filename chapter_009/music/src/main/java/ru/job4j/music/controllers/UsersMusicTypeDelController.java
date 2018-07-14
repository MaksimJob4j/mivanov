package ru.job4j.music.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.MusicLogic;
import ru.job4j.music.dao.StoreException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMusicTypeDelController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UsersMusicTypeDelController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        int typeId = Integer.parseInt(req.getParameter("type-id"));
        int userId = Integer.parseInt(req.getParameter("user-id"));
        try {
            logic.delMusicPref(userId, typeId);
            new JSONHandler().makeMusicTypeJSONResponse(req, resp, userId);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}