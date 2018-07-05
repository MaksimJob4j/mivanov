package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMusicTypeDelController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.UsersMusicTypeDelController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        int typeId = Integer.parseInt(req.getParameter("type-id"));
        int userId = Integer.parseInt(req.getParameter("user-id"));

        try {
            logic.delMusicPref(userId, typeId);
        } catch (StoreException e) {
            LOGGER.traceEntry();
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
        }

        new JSONHandler().makeMusicTypeJSONResponse(req, resp, userId);
    }
}