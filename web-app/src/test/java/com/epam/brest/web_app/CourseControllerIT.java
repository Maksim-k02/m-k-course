package com.epam.brest.web_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional

class CourseControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnCoursesPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/courses")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("courses"))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("courseId", is(1)),
                                hasProperty("courseName", is("Java")),
                                hasProperty("countStudent", is(BigDecimal.valueOf(3)))
                        )
                )))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("courseId", is(2)),
                                hasProperty("courseName", is("Maven")),
                                hasProperty("countStudent", is(BigDecimal.valueOf(2)))
                        )
                )))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("courseId", is(3)),
                                hasProperty("courseName", is("PHP")),
                                hasProperty("countStudent", is(BigDecimal.valueOf(0)))
                        )
                )));

    }
}