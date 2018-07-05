package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ListController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.ListController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        LOGGER.debug("Query: " + req.getQueryString());
        List<User> usersList = null;
        try {
            usersList = logic.findAllUsers();
            usersList.sort(Comparator.comparingInt(User::getId));
        } catch (StoreException e) {
            LOGGER.error("error", e);
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
        }
        req.setAttribute("users", usersList);
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }
}