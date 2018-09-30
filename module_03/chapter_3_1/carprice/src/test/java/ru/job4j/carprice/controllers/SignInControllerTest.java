package ru.job4j.carprice.controllers;

import liquibase.exception.LiquibaseException;
import org.junit.Test;
import ru.job4j.carprice.CarTestHelper;
import ru.job4j.carprice.Logic;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

import static org.mockito.Mockito.*;

public class SignInControllerTest {

    private final Logic logic = Logic.getInstance();

    @Test
    public void doGet() throws ServletException {
        SignInController controller = new SignInController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = new RequestDispatcher() {
            @Override
            public void forward(ServletRequest request, ServletResponse response) {

            }

            @Override
            public void include(ServletRequest request, ServletResponse response) {

            }
        };
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);
        try {
            controller.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/login.jsp");
    }

    @Test
    public void signInTest() throws IOException, ServletException, StoreException, LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
        String login = "test_user";
        User user = logic.findUserByLogin(login);
        String psw = user.getPassword();
        SignInController controller = spy(new SignInController());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(psw);
        when(request.getParameter("new_user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        // right psw
        controller.doPost(request, response);
        verify(request, times(1)).getSession();
        verify(session, times(1)).setAttribute("loginUser", user);
        verify(controller, never()).doGet(request, response);
        verify(request, never()).setAttribute("error", "Invalid sign in!");
        // wrong psw
        String wrongPsw = "wrongPsw";
        when(request.getParameter("password")).thenReturn(wrongPsw);
        doThrow(new IOException()).when(controller).doGet(request, response);
        try {
            controller.doPost(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getSession();
        verify(session, times(1)).setAttribute("loginUser", user);
        verify(request, times(1)).setAttribute("error", "Invalid sign in!");
        verify(controller, times(1)).doGet(request, response);
    }

    @Test
    public void newUserTest() throws IOException, ServletException, StoreException {
        String login = getRandomNewLogin();
        String psw = "testPsw";
        SignInController controller = spy(new SignInController());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(psw);
        when(request.getParameter("new_user")).thenReturn("true");
        when(request.getSession()).thenReturn(session);
        controller.doPost(request, response);
        User user = logic.findUserByLogin(login);
        verify(request, times(1)).getSession();
        verify(session, times(1)).setAttribute("loginUser", user);
        verify(request, never()).setAttribute("error", "Login already used!");
        verify(controller, never()).doGet(request, response);
        // same login
        doThrow(new IOException()).when(controller).doGet(request, response);
        try {
            controller.doPost(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getSession();
        verify(session, times(1)).setAttribute("loginUser", user);
        verify(request, times(1)).setAttribute("error", "Login already used!");
        verify(controller, times(1)).doGet(request, response);
    }

    private String getRandomNewLogin() throws StoreException {
        String resultLogin;
        do {
            int length = new Random().nextInt(5) + 5;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < length; i++) {
                result.append(new Random().nextInt(9));
            }
            resultLogin = result.toString();
        } while (logic.findUserByLogin(resultLogin) != null);
        return resultLogin;
    }
}