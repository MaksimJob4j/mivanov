package ru.job4j.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.MusicLogic;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.MusicType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class JSONHandler {
    private final static Logger LOGGER = LogManager.getLogger(JSONHandler.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    void makeMusicTypeJSONResponse(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        LOGGER.traceEntry();
        Set<MusicType> userTypes = new TreeSet<>(Comparator.comparing(MusicType::getName));
        Set<MusicType> selectTypes = new TreeSet<>(Comparator.comparing(MusicType::getName));
        try {
            userTypes.addAll(logic.findAllTypes(logic.findUserById(String.valueOf(userId))));
            selectTypes.addAll(logic.findAllTypes());
            selectTypes.removeAll(userTypes);
            resp.setContentType("text/json");
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, new UserTypes(userTypes, selectTypes));
            writer.flush();
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class UserTypes {
        Collection<MusicType> userTypes;
        Collection<MusicType> selectTypes;
    }
}