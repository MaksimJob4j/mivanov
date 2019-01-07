package ru.job4j.carpricesprboot.web.controllers;

import org.assertj.core.util.Lists;
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
import ru.job4j.carpricesprboot.service.CarService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarFilterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @Test
    @WithMockUser
    public void whenGetCarFilterAndThereAreNoCarsThenEmptyJsonReturn() throws Exception {
        given(
                this.carService.findCars(1, false, false)
        ).willReturn(
                new ArrayList<>()
        );

        this.mvc.perform(
                get("/carFilter")
                        .param("brand", "1")
                        .param("date", String.valueOf(false))
                        .param("photo", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                jsonPath("$", hasSize(0))
        );
    }

    @Test
    @WithMockUser
    public void whenGetCarFilterThenJsonOfCarssReturn() throws Exception {
        List<Car> cars = new ArrayList<>(Lists.newArrayList(
                Car.builder().id(1).build(),
                Car.builder().id(2).build(),
                Car.builder().id(3).build()
        ));

        given(
                this.carService.findCars(1, false, false)
        ).willReturn(
                cars
        );

        this.mvc.perform(
                get("/carFilter")
                        .param("brand", "1")
                        .param("date", String.valueOf(false))
                        .param("photo", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                jsonPath("$", hasSize(3))
        ).andExpect(
                jsonPath("$[0].id", is(1))
        ).andExpect(
                jsonPath("$[1].id", is(2))
        ).andExpect(
                jsonPath("$[2].id", is(3))
        );
    }
}