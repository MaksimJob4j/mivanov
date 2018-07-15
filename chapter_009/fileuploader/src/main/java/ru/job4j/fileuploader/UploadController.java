package ru.job4j.fileuploader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class UploadController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.fileuploader.UploadController.class);

    private static final String SAVE_DIR = "uploadFiles";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        // gets absolute path of the web application
        String appPath = req.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        try {
            // creates the save directory if it does not exists
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String fileName = "";
            for (Part part : req.getParts()) {
                fileName = extractFileName(part);
                // refines the fileName in case it is an absolute path
                fileName = new File(fileName).getName();
                part.write(savePath + File.separator + fileName);
            }
            req.setAttribute("message", String.format("Файл %s загружен", fileName));
        } catch (Exception e) {
            LOGGER.error("error", e);
            req.setAttribute("error", "Ошибка загрузки файла");
        }
        req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req, resp);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String result = "";
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                result = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return result;
    }
}