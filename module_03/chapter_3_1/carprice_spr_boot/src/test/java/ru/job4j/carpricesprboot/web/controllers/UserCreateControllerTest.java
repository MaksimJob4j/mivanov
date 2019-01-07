package ru.job4j.carpricesprboot.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carpricesprboot.domain.Role;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.service.RoleService;
import ru.job4j.carpricesprboot.service.UserService;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserCreateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser
    public void whenUserGetCreateUserThenForbiddenUrlReturn() throws Exception {
        this.mvc.perform(
                get("/createUser")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                forwardedUrl("/signin")
        ).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenAdminGetCreateUserThenCreateUserTemplatesReturn() throws Exception {
        this.mvc.perform(
                get("/createUser")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                view().name("createUser")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser
    public void whenUserPostCreateUserThenForbiddenUrlReturn() throws Exception {
        this.mvc.perform(
                post("/createUser")
                        .param("login", "testUser")
                        .param("password", "testPassword")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                forwardedUrl("/signin")
        ).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenAdminPostCreateUserWithExistUserThenUserIsNotCreated() throws Exception {
        String login = "testLogin";
        given(
                this.userService.findByLogin(login)
        ).willReturn(
                new User()
        );
        this.mvc.perform(
                post("/createUser")
                .param("login", login)
                .param("password", "testPassword")
                .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("createUser")
        ).andExpect(
                model().attributeExists("error")
        ).andExpect(
                model().attributeDoesNotExist("msg")
        );

        verify(userService, times(1)).findByLogin(login);
        verify(userService, never()).create(isA(User.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenAdminPostCreateUserWithNonexistentUserThenUserIsCreated() throws Exception {
        String login = "testLogin";
        given(
                this.userService.findByLogin(login)
        ).willReturn(
                null
        );
        given(
                this.roleService.findByName("ROLE_USER")
        ).willReturn(
                new Role()
        );
        this.mvc.perform(
                post("/createUser")
                .param("login", login)
                .param("password", "testPassword")
                .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("createUser")
        ).andExpect(
                model().attributeExists("msg")
        ).andExpect(
                model().attributeDoesNotExist("error")
        );

        verify(userService, times(1)).findByLogin(login);
        verify(userService, times(1)).create(isA(User.class));
    }
}