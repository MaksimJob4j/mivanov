package ru.job4j.carpricesprboot.web.controllers;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.domain.description.Brand;
import ru.job4j.carpricesprboot.service.EntityService;
import ru.job4j.carpricesprboot.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainListControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private EntityService<Brand> brandService;


    @Test
    @WithAnonymousUser
    public void whenListAnonymousThenRedirectToLogin() throws Exception {
        this.mvc.perform(
                get("/list")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                redirectedUrlPattern("**/signin")
        ).andExpect(
                status().isFound()
        );
    }

    @Test
    @WithMockUser()
    public void whenListLoggedInUserThenRedirectToMain() throws Exception {
        this.mvc.perform(
                get("/list")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                redirectedUrl("/")
        ).andExpect(
                status().isFound()
        );
    }

    @Test
    @WithAnonymousUser
    public void whenMainListThenReturnMainList() throws Exception {
        given(
                this.brandService.findOrdered()
        ).willReturn(
                Lists.newArrayList(new Brand())
        );
        given(
                this.userService.getLoginUserBySecurityContext(SecurityContextHolder.getContext())
        ).willReturn(
                new User()
        );
        this.mvc.perform(
                get("/")
                        .accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("mainList")
        ).andExpect(
                model().attributeExists("brands")
        ).andExpect(
                model().attributeExists("loginUser")
        );
    }
}