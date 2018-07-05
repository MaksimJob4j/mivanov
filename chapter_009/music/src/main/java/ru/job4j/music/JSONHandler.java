package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.MusicType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class JSONHandler {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.JSONHandler.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    void makeMusicTypeJSONResponse(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        Set<MusicType> userTypes = new TreeSet<>(Comparator.comparing(MusicType::getName));
        Set<MusicType> types = new TreeSet<>(Comparator.comparing(MusicType::getName));
        try {
            userTypes.addAll(logic.findAllTypes(logic.findUserById(String.valueOf(userId))));
            types.addAll(logic.findAllTypes());
            types.removeAll(userTypes);
        } catch (StoreException e) {
            LOGGER.traceEntry();
            req.setAttribute("error", "DataBase ERROR: " + e.getMessage());
        }
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"userTypes\": [");
        if (userTypes.size() > 0) {
            for (MusicType type : userTypes) {
                str.append("{\"id\" : \"" + type.getId() + "\", \"name\": \"" + type.getName() + "\"}, ");
            }
            str.delete(str.length() - 2, str.length());
        }
        str.append("], ");
        str.append("\"selectTypes\": [");
        if (types.size() > 0) {
            for (MusicType type : types) {
                str.append("{\"id\" : \"" + type.getId() + "\", \"name\": \"" + type.getName() + "\"}, ");
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