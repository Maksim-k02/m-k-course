package com.epam.brest.web_app;

import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class CourseControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseService courseService;

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
                .andExpect(status().isOk())
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
    @Test
    void shouldAddCourse() throws Exception {
        // WHEN
        assertNotNull(courseService);
        Integer courseSizeBefore = courseService.count();
        assertNotNull(courseSizeBefore);
        Course course = new Course("Spring123");

        //THEN
        //Integer newCourseId = courseService.create(course)
        mockMvc.perform(
                MockMvcRequestBuilders.post("/course")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseName", course.getCourseName())
                ).andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/courses"))
                    .andExpect(redirectedUrl("/courses"));
        ;

        assertEquals(courseSizeBefore, courseService.count() - 1);
    }
}