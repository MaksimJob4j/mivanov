package ru.job4j.carpricesprboot.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carpricesprboot.service.CarService;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersCarsController.class)
public class UsersCarsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @Test
    @WithMockUser("TestUserLogin")
    public void whenUsersCarsThenUsersCarsTemplatesReturn() throws Exception {
        given(
                this.carService.findByUserLogin("TestUserLogin")
        ).willReturn(
                new ArrayList<>()
        );

        this.mvc.perform(
                get("/usersCars")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("usersCars")
        ).andExpect(
                model().attributeExists("users_cars")
        );

        verify(this.carService, times(1)).findByUserLogin("TestUserLogin");
    }
}