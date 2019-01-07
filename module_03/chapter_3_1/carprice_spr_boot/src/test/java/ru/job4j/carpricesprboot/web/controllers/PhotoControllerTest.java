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
import ru.job4j.carpricesprboot.domain.description.Photo;
import ru.job4j.carpricesprboot.service.PhotoService;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService photoService;

    @Test
    @WithMockUser
    public void whenGetPhotoAndThereIsNoPhotoThenDoNothing() throws Exception {
        given(
                this.photoService.findByCar(1)
        ).willReturn(
                null
        );

        this.mvc.perform(
                get("/photo")
                        .param("car_id", "1")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string("")
        );
    }

    @Test
    @WithMockUser
    public void whenGetPhotoThenReturnData() throws Exception {
        byte[] data = new byte[] {1, 2, 3};
        Photo photo = new Photo(1, "TestPhoto.jpg", data, new Car());
        given(
                this.photoService.findByCar(1)
        ).willReturn(
                new ArrayList<>(
                        Lists.newArrayList(photo)
                )
        );

        this.mvc.perform(
                get("/photo")
                        .param("car_id", "1")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().contentType(MediaType.IMAGE_JPEG)
        ).andExpect(
                content().bytes(data)
        ).andExpect(
                header().longValue("Content-Length", data.length)
        );
    }
}