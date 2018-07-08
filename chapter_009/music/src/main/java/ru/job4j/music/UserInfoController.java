package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Address;
import ru.job4j.music.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserInfoController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.UserInfoController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        try {
            User user = (User) req.getAttribute("user");
            if (user == null) {
                user = logic.findUserById(req.getParameter("id"));
                req.setAttribute("user", user);
                req.setAttribute("types", logic.findAllTypes().toArray());
            }
            Address address = user.getAddress();
            if (address == null) {
                address = new Address();
            }
            req.setAttribute("address", address);
            req.getRequestDispatcher("/WEB-INF/views/info.jsp").forward(req, resp);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}