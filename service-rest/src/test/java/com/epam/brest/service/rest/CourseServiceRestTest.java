package com.epam.brest.service.rest;

import com.epam.brest.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class CourseServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceRestTest.class);

    public static final String COURSES_URL = "http://localhost:8088/courses";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    CourseServiceRest courseService;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        courseService = new CourseServiceRest(COURSES_URL, restTemplate);
    }

    @Test
    public void shouldFindAllCourses() throws Exception {

        LOGGER.debug("shouldFindAllCourses()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Course> courses = courseService.findAll();

        // then
        mockServer.verify();
        assertNotNull(courses);
        assertTrue(courses.size() > 0);
    }

    @Test
    public void shouldCreateCourse() throws Exception {

        LOGGER.debug("shouldCreateCourse()");
        // given
        Course course = new Course()
                .setCourseName(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = courseService.create(course);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldFindCourseById() throws Exception {

        // given
        Integer id = 1;
        Course course = new Course()
                .setCourseId(id)
                .setCourseName(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(course))
                );

        // when
        Course resultCourse = courseService.getCourseById(id);

        // then
        mockServer.verify();
        assertNotNull(resultCourse);
        assertEquals(resultCourse.getCourseId(), id);
        assertEquals(resultCourse.getCourseName(), course.getCourseName());
    }

    @Test
    public void shouldUpdateCourse() throws Exception {

        // given
        Integer id = 1;
        Course course = new Course()
                .setCourseId(id)
                .setCourseName(RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(course))
                );

        // when
        int result = courseService.update(course);
        Course updatedCourse = courseService.getCourseById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertNotNull(updatedCourse);
        assertEquals(updatedCourse.getCourseId(), id);
        assertEquals(updatedCourse.getCourseName(), course.getCourseName());
    }

    @Test
    public void shouldDeleteCorse() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = courseService.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    private Course create(int index) {
        Course course = new Course();
        course.setCourseId(index);
        course.setCourseName("d" + index);
        return course;
    }
}