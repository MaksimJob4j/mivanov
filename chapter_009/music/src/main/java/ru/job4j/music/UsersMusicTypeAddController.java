package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.MusicType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMusicTypeAddController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.UsersMusicTypeAddController.class);

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
        } catch (StoreException e) {
            LOGGER.traceEntry();
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
        }
        new JSONHandler().makeMusicTypeJSONResponse(req, resp, userId);
    }
}