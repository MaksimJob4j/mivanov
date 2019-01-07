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
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.domain.description.Brand;
import ru.job4j.carpricesprboot.domain.description.Model;
import ru.job4j.carpricesprboot.service.CarService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarInfoController.class)
public class CarInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @Test
    @WithMockUser()
    public void whenCarAndCarFoundThenCarTemplatesReturn() throws Exception {
        Car car = new Car();
        Brand brand = new Brand();
        Model model = new Model();
        model.setBrand(brand);
        car.setModel(model);
        given(
                this.carService.findNotNull(1)
        ).willReturn(
                car
        );

        this.mvc.perform(
                get("/car")
                        .param("id", "1")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("car")
        ).andExpect(
                model().attributeExists("car_info")
        );

        verify(this.carService, times(1)).findNotNull(1);
    }
}