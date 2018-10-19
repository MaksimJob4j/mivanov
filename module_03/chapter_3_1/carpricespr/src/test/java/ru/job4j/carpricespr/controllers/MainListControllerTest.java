package ru.job4j.carpricespr.controllers;

import liquibase.exception.LiquibaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.carpricespr.CarTestHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainListControllerTest {

    @BeforeClass
    public static void setUpClass() throws LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
    }

    @Test
    public void doGet() throws ServletException, IOException {
        MainListController controller = new MainListController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("loginUser")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");
        RequestDispatcher requestDispatcher = new RequestDispatcher() {
            @Override
            public void forward(ServletRequest request, ServletResponse response) {

            }

            @Override
            public void include(ServletRequest request, ServletResponse response) {

            }
        };
        when(request.getRequestDispatcher("/WEB-INF/views/mainlist.jsp")).thenReturn(requestDispatcher);
        controller.doGet(request, response);

    }
}