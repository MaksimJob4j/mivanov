package ru.job4j.carprice.controllers;

import org.junit.Test;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.description.Photo;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PhotoControllerTest {

    private final Logic logic = Logic.getInstance();

    @Test
    public void doGet() throws StoreException, IOException {
        PhotoController controller = spy(new PhotoController());
        Car car = new Car();
        logic.createCar(car);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("car_id")).thenReturn(car.getId().toString());
        // no photos
        controller.doGet(request, response);
        verify(request, times(1)).getParameter("car_id");
        verify(response, never()).getOutputStream();
        // with photos
        Photo photo = new Photo();
        byte[] fileData = {1, 2, 3};
        photo.setFileData(fileData);
        photo.setCar(car);
        logic.createPhoto(photo);
        ServletContext servletContext = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getMimeType(null)).thenReturn("");
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        controller.doGet(request, response);
        verify(request, times(2)).getParameter("car_id");
        verify(response, times(1)).getOutputStream();
    }
}