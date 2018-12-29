package ru.job4j.carpricesprboot.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.carpricesprboot.domain.description.Photo;
import ru.job4j.carpricesprboot.service.PhotoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PhotoController {
    private final static Logger LOGGER = LogManager.getLogger(PhotoController.class);
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photo")
    protected void getPhotoFile(@RequestParam("car_id") String carID,
                                HttpServletResponse resp) throws IOException {
        LOGGER.traceEntry();

        Photo photo = null;
        List<Photo> photoList = photoService.findByCar(Integer.parseInt(carID));
        if (photoList != null && photoList.size() > 0) {
            photo = photoList.get(0);
        }
        if (photo != null) {
            String imageFileName = photo.getFileName();
            String contentType = Files.probeContentType(Paths.get(imageFileName));
            resp.setHeader("Content-Type", contentType);
            resp.setHeader("Content-Length", String.valueOf(photo.getFileData().length));
            resp.setHeader("Content-Disposition", "inline; filename=\"" + imageFileName + "\"");
            resp.getOutputStream().write(photo.getFileData());
        }
    }
}