package ru.job4j.carprice.controllers;

import liquibase.exception.LiquibaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.carprice.CarTestHelper;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CarCreateControllerTest {

    private final Logic logic = Logic.getInstance();

    @BeforeClass
    public static void setUpClass() throws LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
    }

    @Test
    public void doGet() throws ServletException, IOException {
        CarCreateController controller = new CarCreateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/createcar.jsp")).thenReturn(requestDispatcher);
        doThrow(new IOException()).when(requestDispatcher).forward(request, response);
        try {
            controller.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/createcar.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void doPost() throws StoreException, IOException {
        CarCreateController controller = new CarCreateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        ServletContext servletContext = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(session.getAttribute("loginUser")).thenReturn(logic.findUserByLogin("test_user"));
        controller.doPost(request, response);
    }
}