package ru.job4j.carpricespr.controllers;

import liquibase.exception.LiquibaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.carpricespr.CarTestHelper;
import ru.job4j.carpricespr.Logic;
import ru.job4j.carpricespr.dao.StoreException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UsersCarsControllerTest {
/*

    private final Logic logic = Logic.getInstance();

    @BeforeClass
    public static void setUpClass() throws LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
    }

    @Test
    public void doGet() throws ServletException, StoreException, IOException {
        UsersCarsController controller = new UsersCarsController(logic);
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
        when(request.getRequestDispatcher("/WEB-INF/views/userscars.jsp")).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        when(session.getAttribute("loginUser")).thenReturn(logic.findUserByLogin("test_user"));
        try {
            controller.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}