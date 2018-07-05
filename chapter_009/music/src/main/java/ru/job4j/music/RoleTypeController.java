package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RoleTypeController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.RoleTypeController.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        List<Role> roles = new ArrayList<>();
        try {
            roles = logic.findAllRoles();
        } catch (StoreException e) {
            LOGGER.traceEntry();
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
        }
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"roles\": [");
        if (roles.size() > 0) {
            for (Role role : roles) {
                str.append("{\"id\" : \"" + role.getId() + "\", \"name\": \"" + role.getName() + "\", \"level\": \"" + role.getLevel() + "\"}, ");
            }
            str.delete(str.length() - 2, str.length());
        }
        str.append("]");
        str.append("}");

        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(str.toString());
        writer.flush();
    }

}