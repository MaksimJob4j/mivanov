package ru.job4j.userservlet;

import org.junit.Test;
import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDeleteControllerTest {
    private final ValidateService users = ValidateService.getInstance();

    @Test
    public void deleteTest() throws UserException, ServletException, IOException {
        TestHelper.getTestUser();
        User user = users.findByLogin("test_user");
        assertThat(user == null, is(false));
        UserDeleteController controller = new UserDeleteController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn(user.getId());
        controller.doPost(request, response);
        user = users.findByLogin("test_user");
        assertThat(user == null, is(true));
    }
}