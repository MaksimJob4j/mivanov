package ru.job4j.carpricesprboot.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.domain.CarParts;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.domain.description.Photo;
import ru.job4j.carpricesprboot.service.CarPartsService;
import ru.job4j.carpricesprboot.service.CarService;
import ru.job4j.carpricesprboot.service.PhotoService;
import ru.job4j.carpricesprboot.service.UserService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarCreateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarPartsService carPartsService;
    @MockBean
    private CarService carService;
    @MockBean
    private UserService userService;
    @MockBean
    private PhotoService photoService;

    @Test
    @WithMockUser
    public void whenGetNewCarThenViewCreateCar() throws Exception {
        given(
                this.carPartsService.pickParts()
        ).willReturn(
                new CarParts(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
        this.mvc.perform(
                get("/newCar")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("createCar")
        ).andExpect(
                model().attributeExists("carPats")
        );
    }

    @Test
    @WithMockUser
    public void whenPostNewCarWithoutPhotoThenCreateCarWithoutPhoto() throws Exception {
        performPost(new byte[0]);
        verify(this.carService, times(1)).create(isA(Car.class));
        verify(this.photoService, never()).create(isA(Photo.class));
    }

    @Test
    @WithMockUser
    public void whenPostNewCarWithPhotoThenCreateCarWithPhoto() throws Exception {
        performPost(new byte[1]);
        verify(this.carService, times(1)).create(isA(Car.class));
        verify(this.photoService, times(1)).create(isA(Photo.class));
    }

    private void performPost(byte[] content) throws Exception {
        Car car = new Car();
        User user = new User();
        MockMultipartFile file = new MockMultipartFile("photofile", content);

        given(
                this.userService.getLoginUserBySecurityContext(SecurityContextHolder.getContext())
        ).willReturn(
                user
        );

        this.mvc.perform(
                MockMvcRequestBuilders.multipart("/newCar")
                        .file(file)
                        .requestAttr("car", car)
                        .accept(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(
                redirectedUrl("/usersCars")
        ).andExpect(
                status().isFound()
        );
    }
}