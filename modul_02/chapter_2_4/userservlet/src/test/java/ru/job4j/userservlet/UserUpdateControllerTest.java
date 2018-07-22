package ru.job4j.userservlet;

import org.junit.Test;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserUpdateControllerTest {
    private final ValidateService users = ValidateService.getInstance();

    @Test
    public void updateTest() throws Exception {
        TestHelper.getTestUser();
        UserUpdateController controller = new UserUpdateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn(users.findByLogin("test_user").getId());
        when(request.getParameter("password")).thenReturn("test_update_password");
        controller.doPost(request, response);
        User newUser = users.findByLogin("test_user");
        assertThat(newUser.getPassword(), is("test_update_password"));
    }
}