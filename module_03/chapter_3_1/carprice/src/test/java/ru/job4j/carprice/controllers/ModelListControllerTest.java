package ru.job4j.carprice.controllers;

import liquibase.exception.LiquibaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.carprice.CarTestHelper;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelListControllerTest {

    private final Logic logic = Logic.getInstance();

    @BeforeClass
    public static void setUpClass() throws LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
    }

    @Test
    public void doGet() throws IOException, StoreException {
        ModelListController controller = new ModelListController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("brand")).thenReturn(
                logic.findModels().get(0).getBrand().getId().toString());
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        controller.doGet(request, response);
    }
}