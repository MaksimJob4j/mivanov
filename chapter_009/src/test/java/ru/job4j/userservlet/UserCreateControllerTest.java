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
    public void addTest() throws UserException, ServletException, IOException {
        TestHelper.getTestUser();
        User user = users.findByLogin("test_user");
        assertThat(user == null, is(false));
        users.delete(user.getId());
        user = users.findByLogin("test_user");
        assertThat(user == null, is(true));
        UserCreateController controller = new UserCreateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("test_user");
        when(request.getParameter("password")).thenReturn("test_password");
        when(request.getParameter("role")).thenReturn("user");
        controller.doPost(request, response);
        user = users.findByLogin("test_login");
        assertThat(user.getPassword(), is("test_password"));
    }
}