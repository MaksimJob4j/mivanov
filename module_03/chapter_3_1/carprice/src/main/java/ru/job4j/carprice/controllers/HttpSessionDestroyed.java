package ru.job4j.carprice.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.io.IOException;

public class HttpSessionDestroyed implements HttpSessionListener {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.controllers.HttpSessionDestroyed.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.traceEntry();

        HttpSession session = se.getSession();
        String tempPhotoDirString = session.getServletContext().getRealPath("")
                + File.separator
                + session.getServletContext().getInitParameter("tempFolder")
                + File.separator
                + session.getId();
        File tempPhotoDir = new File(tempPhotoDirString);
        if (tempPhotoDir.exists()) {
            try {
                FileUtils.deleteDirectory(tempPhotoDir);
            } catch (IOException e) {
                LOGGER.error("error", e);
            }
        }
    }
}