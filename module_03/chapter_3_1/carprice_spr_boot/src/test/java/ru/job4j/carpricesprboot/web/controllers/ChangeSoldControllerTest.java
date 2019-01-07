package ru.job4j.carpricesprboot.web.controllers;

import org.junit.Before;
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
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.repository.exceptions.NoCarException;
import ru.job4j.carpricesprboot.service.CarService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChangeSoldControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    private final Car car = new Car();

    @Before
    public void setup() throws NoCarException {
        User user = new User();
        user.setLogin("TestUserLogin");
        this.car.setOwner(user);
        given(
                this.carService.findNotNull(1)
        ).willReturn(
                this.car
        );
    }

    @Test
    @WithMockUser("TestUserLogin")
    public void whenOwnerIsPrincipalThenChangeSold() throws Exception {
        performChangeSold();
        verify(this.carService, times(1)).findNotNull(1);
        verify(this.carService, times(1)).update(this.car);
        assertFalse(this.car.getSold());
    }

    @Test
    @WithMockUser("WrongTestUserLogin")
    public void whenOwnerIsNotPrincipalThenDoNotChangeSold() throws Exception {
        performChangeSold();
        verify(this.carService, times(1)).findNotNull(1);
        verify(this.carService, never()).update(this.car);
        assertTrue(this.car.getSold());
    }

    private void performChangeSold() throws Exception {
        this.car.setSold(true);
        this.mvc.perform(
                post("/changeSold")
                        .param("car_id", String.valueOf(1))
                        .param("sold", String.valueOf(false))
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        );
    }
}