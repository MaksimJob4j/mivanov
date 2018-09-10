package ru.job4j.carprice.controllers;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.carprice.items.description.Photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarCreateController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.controllers.CarCreateController.class);
    private final Logic logic = Logic.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        try {
            req.setAttribute("brands", logic.findBrands());
            req.setAttribute("bodies", logic.findBodies());
            req.setAttribute("colors", logic.findColors());
            req.setAttribute("transmissions", logic.findTransmissions());
            req.setAttribute("engines", logic.findEngines());
            req.setAttribute("drives", logic.findDrives());
            req.getRequestDispatcher("/WEB-INF/views/createcar.jsp").forward(req, resp);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        String tempFolder = getTempFolder(req);
        String photoPath;
        File photoFile = null;
        String photoFileName = null;
        Map<String, String> parameters = new HashMap<>();
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    parameters.put(item.getFieldName(), item.getString());
                } else {
                    if (photoFileName == null) {
                        photoFileName = FilenameUtils.getName(item.getName());
                    }
                    if (photoFileName != null
                            && !"".equals(photoFileName)
                            && photoFileName.equals(FilenameUtils.getName(item.getName()))) {
                        if (photoFile == null) {
                            photoPath = tempFolder + File.separator
                                    + photoFileName;
                            photoFile = new File(photoPath);
                        }
                        item.write(photoFile);
                    }
                }
            }
            Car car = logic.createCarFromParameters(parameters, loginUser);
            if (photoFile != null) {
                Photo photo = getPhotoFromFile(car, photoFile);
                logic.createPhoto(photo);
                photoFile.delete();
            }
            resp.sendRedirect(String.format("%s/users_cars", req.getContextPath()));
        } catch (Exception e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }

    private Photo getPhotoFromFile(Car car, File photoFile) throws IOException {
        LOGGER.traceEntry();

        Photo photo = new Photo();
        photo.setFileName(photoFile.getName());
        photo.setFileData(Files.readAllBytes(photoFile.toPath()));
        photo.setCar(car);
        return photo;
    }

    private String getTempFolder(HttpServletRequest req) {
        LOGGER.traceEntry();

        String tempFolderString = this.getServletContext().getRealPath("")
                + File.separator
                + this.getServletContext().getInitParameter("tempFolder");
        File tempFolder = new File(tempFolderString);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }
        String photoDirString = tempFolderString + File.separator + req.getSession().getId();
        File photoDir = new File(photoDirString);
        if (!photoDir.exists()) {
            photoDir.mkdir();
        }
        return photoDirString;
    }
}