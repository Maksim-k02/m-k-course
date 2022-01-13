package com.epam.brest.service.impl;

import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class CourseServiceImplIT {

    @Autowired
    CourseService courseService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCount(){
        assertNotNull(courseService);
        Integer quantity = courseService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3),quantity);
    }

    @Test
    void create(){
        assertNotNull(courseService);
        Integer courseSizeBefore = courseService.count();
        assertNotNull(courseSizeBefore);
        Course course = new Course("Payton");
        Integer newCourseId = courseService.create(course);
        assertNotNull(newCourseId);
        assertEquals(courseSizeBefore, courseService.count()-1);
    }

    @Test
    void tryToCreateEqualsCourses(){
        assertNotNull(courseService);
        Course course = new Course("Payton");

        assertThrows(IllegalArgumentException.class, () -> {
            courseService.create(course);
            courseService.create(course);
        });
    }
}