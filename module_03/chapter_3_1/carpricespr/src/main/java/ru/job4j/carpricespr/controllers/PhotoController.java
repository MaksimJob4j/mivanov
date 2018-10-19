package ru.job4j.carpricespr.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.items.description.Photo;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PhotoController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(PhotoController.class);
    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();

        Photo photo = null;
        try {
            List<Photo> photoList = logic.findPhotoByCar(req.getParameter("car_id"));
            if (photoList != null && photoList.size() > 0) {
                photo = photoList.get(0);
            }
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
        if (photo != null) {
            String imageFileName = photo.getFileName();
            String contentType = req.getServletContext().getMimeType(imageFileName);
            resp.setHeader("Content-Type", contentType);
            resp.setHeader("Content-Length", String.valueOf(photo.getFileData().length));
            resp.setHeader("Content-Disposition", "inline; filename=\"" + imageFileName + "\"");
            resp.getOutputStream().write(photo.getFileData());
        }
    }
}