package com.epam.brest.rest;

import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CourseDtoControllerTest {

    @InjectMocks
    private CourseDtoController courseDtoController;

    @Mock
    private CourseDtoService courseDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(courseDtoService);
    }

    @Test
    public void shouldFindAllWithCountStudent() throws Exception {

        Mockito.when(courseDtoService.findAllWithCountStudent()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/course_dtos")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseName", Matchers.is("d0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].countStudent", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseName", Matchers.is("d1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].countStudent", Matchers.is(101)))
        ;

        Mockito.verify(courseDtoService).findAllWithCountStudent();
    }

    private CourseDto create(int index) {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseId(index);
        courseDto.setCourseName("d" + index);
        courseDto.setCountStudent(BigDecimal.valueOf(100 + index));
        return courseDto;
    }
}
