package ru.job4j.carpricesprboot.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignInControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenSigninWithoutParamThenLoginTemplatesWithoutAttributeReturn() throws Exception {

        this.mvc.perform(
                get("/signin")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("login")
        ).andExpect(
                model().attributeDoesNotExist("error")
        ).andExpect(
                model().attributeDoesNotExist("msg")
        );
    }

    @Test
    public void whenSigninWithErrorParamThenLoginTemplatesWithErrorAttributeReturn() throws Exception {

        this.mvc.perform(
                get("/signin")
                        .param("error", "TestErrorText")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("login")
        ).andExpect(
                model().attributeExists("error")
        ).andExpect(
                model().attributeDoesNotExist("msg")
        );
    }

    @Test
    public void whenSigninWithLogoutParamThenLoginTemplatesWithErrorAttributeReturn() throws Exception {

        this.mvc.perform(
                get("/signin")
                        .param("logout", "TestText")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("login")
        ).andExpect(
                model().attributeDoesNotExist("error")
        ).andExpect(
                model().attributeExists("msg")
        );
    }
}