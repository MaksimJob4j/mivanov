package ru.job4j.userservlet;

import org.junit.Test;
import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserCreateControllerTest {
    private final ValidateService users = ValidateService.getInstance();

    @Test
    public void addTest() {
        TestHelper.getTestUser();

        User user = null;
        try {
            user = users.findByLogin("test_user");
        } catch (UserException e) {
            e.printStackTrace();
        }

        assertThat(user == null, is(false));

        try {
            users.delete(user.getId());
        } catch (UserException e) {
            e.printStackTrace();
        }

        user = null;

        try {
            user = users.findByLogin("test_user");
        } catch (UserException e) {
            e.printStackTrace();
        }

        assertThat(user == null, is(true));

        UserCreateController controller = new UserCreateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("login")).thenReturn("test_user");
        when(request.getParameter("password")).thenReturn("test_password");
        when(request.getParameter("role")).thenReturn("user");

        try {
            controller.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        user = null;
        try {
            user = users.findByLogin("test_login");
        } catch (UserException e) {
            e.printStackTrace();
        }

        assertThat(user.getPassword(), is("test_password"));
    }
}