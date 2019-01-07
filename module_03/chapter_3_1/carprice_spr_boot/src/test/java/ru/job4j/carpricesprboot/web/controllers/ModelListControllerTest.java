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
import ru.job4j.carpricesprboot.domain.description.Brand;
import ru.job4j.carpricesprboot.domain.description.Model;
import ru.job4j.carpricesprboot.service.ModelService;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModelListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ModelService  modelService;

    @Test
    @WithMockUser
    public void whenGetModelsAndThereAreNoModelsThenEmptyJsonReturn() throws Exception {
        given(
                this.modelService.findByBrandId(1)
        ).willReturn(
                new ArrayList<>()
        );

        this.mvc.perform(
                get("/models")
                        .param("brand", "1")
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
    public void whenGetModelsThenJsonOfModelsReturn() throws Exception {
        int brandId = 1;
        Brand brand = new Brand(brandId, "testBrand", new ArrayList<>());
        brand.getModels().add(new Model(1, "TestModel1", brand));
        brand.getModels().add(new Model(2, "TestModel2", brand));

        given(
                this.modelService.findByBrandId(brandId)
        ).willReturn(
                brand.getModels()
        );

        this.mvc.perform(
                get("/models")
                        .param("brand", String.valueOf(brandId))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                jsonPath("$", hasSize(2))
        ).andExpect(
                jsonPath("$[0].brand.name", is("testBrand"))
        ).andExpect(
                jsonPath("$[0].name", is("TestModel1"))
        ).andExpect(
                jsonPath("$[1].brand.name", is("testBrand"))
        ).andExpect(
                jsonPath("$[1].name", is("TestModel2"))
        );
    }
}