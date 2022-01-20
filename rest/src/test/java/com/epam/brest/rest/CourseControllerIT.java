package com.epam.brest.rest;

import com.epam.brest.model.Course;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;
import static com.epam.brest.rest.exception.CustomExceptionHandler.COURSE_NOT_FOUND;
import static com.epam.brest.rest.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class CourseControllerIT {


    private static final Logger LOGGER = LoggerFactory.getLogger(CourseControllerIT.class);

    public static final String COURSES_ENDPOINT = "/courses";

    @Autowired
    private CourseController courseController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcCourseService courseService = new MockMvcCourseService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllCourses() throws Exception {

        // given
        Course course = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course);

        // when
        List<Course> courses = courseService.findAll();

        // then
        assertNotNull(courses);
        assertTrue(courses.size() > 0);
    }

    @Test
    public void shouldCreateCourse() throws Exception {
        Course course = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course);
        assertNotNull(id);
    }

    @Test
    public void shouldFindCourseById() throws Exception {

        // given
        Course course = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course);

        assertNotNull(id);

        // when
        Optional<Course> optionalCourse = courseService.findById(id);

        // then
        assertTrue(optionalCourse.isPresent());
        assertEquals(optionalCourse.get().getCourseId(), id);
        //assertEquals(course.getCourseName(), optionalCourse.get().getCourseName());
    }

    @Test
    public void shouldUpdateCourse() throws Exception {

        // given
        Course course = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course);
        assertNotNull(id);

        Optional<Course> courseOptional = courseService.findById(id);
        assertTrue(courseOptional.isPresent());

        courseOptional.get().
                setCourseName(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));

        // when
        int result = courseService.update(courseOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Course> updatedCourseOptional = courseService.findById(id);
        assertTrue(updatedCourseOptional.isPresent());
        assertEquals(updatedCourseOptional.get().getCourseId(), id);
        assertEquals(updatedCourseOptional.get().getCourseName(),courseOptional.get().getCourseName());

    }

    @Test
    public void shouldDeleteCourse() throws Exception {
        // given
        Course course = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course);

        List<Course> courses = courseService.findAll();
        assertNotNull(courses);

        // when
        int result = courseService.delete(id);

        // then
        assertTrue(1 == result);

        List<Course> currentCourses = courseService.findAll();
        assertNotNull(currentCourses);

        assertTrue(courses.size()-1 == currentCourses.size());
    }

    @Test
    public void shouldReturnCourseNotFoundError() throws Exception {

        LOGGER.debug("shouldReturnCourseNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(COURSES_ENDPOINT + "/999999")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), COURSE_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateCourseWithDuplicateName() throws Exception {
        Course course1 = new Course(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));
        Integer id = courseService.create(course1);
        assertNotNull(id);

        Course course2 = new Course(course1.getCourseName());

        MockHttpServletResponse response =
                mockMvc.perform(post(COURSES_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(course2))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR);
    }

    class MockMvcCourseService {

        public List<Course> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(COURSES_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Course>>() {});
        }

        public Optional<Course> findById(Integer id) throws Exception {

            LOGGER.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(COURSES_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Course.class));
        }

        public Integer create(Course course) throws Exception {

            LOGGER.debug("create({})", course);
            String json = objectMapper.writeValueAsString(course);
            MockHttpServletResponse response =
                    mockMvc.perform(post(COURSES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Course course) throws Exception {

            LOGGER.debug("update({})", course);
            MockHttpServletResponse response =
                    mockMvc.perform(put(COURSES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(course))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer courseId) throws Exception {

            LOGGER.debug("delete(id:{})", courseId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(COURSES_ENDPOINT).append("/")
                                            .append(courseId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
