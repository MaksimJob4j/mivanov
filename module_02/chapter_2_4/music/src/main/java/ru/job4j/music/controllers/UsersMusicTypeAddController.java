package ru.job4j.music.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.MusicLogic;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.MusicType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMusicTypeAddController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(UsersMusicTypeAddController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        int typeId = Integer.parseInt(req.getParameter("type-id-new") == null ? "-1" : req.getParameter("type-id-new"));
        int userId = Integer.parseInt(req.getParameter("user-id"));
        try {
            if (typeId < 0) {
                String name = req.getParameter("type-name-new");
                if (name != null && !name.equals("")) {
                    MusicType type = logic.findMusicByName(name);
                    if (type == null) {
                        logic.newMusicType(name);
                        type = logic.findMusicByName(name);
                    }
                    typeId = type.getId();
                }
            }
            if (typeId > 0) {
                logic.addMusicPref(userId, typeId);
            }
            new JSONHandler().makeMusicTypeJSONResponse(req, resp, userId);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}