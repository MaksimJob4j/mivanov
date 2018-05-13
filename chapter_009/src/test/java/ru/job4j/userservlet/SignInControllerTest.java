package ru.job4j.userservlet;

import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class SignInControllerTest {
    private final ValidateService users = ValidateService.getInstance();

    @Test
    public void signInRightTest() {

        TestHelper.getTestUser();

        SignInController controller = new SignInController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("login")).thenReturn("test_user");
        when(request.getParameter("password")).thenReturn("test_password");
        when(request.getSession()).thenReturn(session);


        try {
            controller.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(request, times(1)).getSession();
        Mockito.verify(session, times(1)).setAttribute("login", "test_user");
        Mockito.verify(request, never()).setAttribute("error", "Invalid sign in!");

    }

    @Test
    public void signInWrongTest() {
        TestHelper.getTestUser();

        SignInController controller = spy(new SignInController());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Переоперделяем методы мок-объекта
        when(request.getParameter("login")).thenReturn("test_user");
        when(request.getParameter("password")).thenReturn("test_wrong_password");

        // Пробрасываем исключение вместо void метода spy-объекта
        // spy - объект, который не полностью подменяется моком, а лишь переопределяется часть методов.
        try {
            doThrow(new IOException()).when(controller).doGet(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        try {
            controller.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(request, never()).getSession();
        Mockito.verify(request, times(1)).setAttribute("error", "Invalid sign in!");
    }
}