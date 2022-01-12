package com.epam.brest.dao;

import com.epam.brest.model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
@Rollback
class CourseDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(CourseDaoJDBCImplIT.class);

    private CourseDaoJDBCImpl courseDaoJDBC;

    public CourseDaoJDBCImplIT(@Autowired CourseDao courseDaoJDBC) {
        this.courseDaoJDBC = (CourseDaoJDBCImpl) courseDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Execute test: findAll()");
        assertNotNull(courseDaoJDBC);
        assertNotNull(courseDaoJDBC.findAll());
    }

    @Test
    void create(){
        assertNotNull(courseDaoJDBC);
        int courseSizeBefore = courseDaoJDBC.count();
        Course course = new Course("Payton");
        Integer newCourseId = courseDaoJDBC.create(course);
        assertNotNull(newCourseId);
        assertEquals((int) courseSizeBefore, courseDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateEqualsCourses(){
        assertNotNull(courseDaoJDBC);
        Course course = new Course("Kotlin");

        assertThrows(IllegalArgumentException.class, () -> {
            courseDaoJDBC.create(course);
            courseDaoJDBC.create(course);
        });
    }

    @Test
    void getCourseById(){
        List<Course> courses = courseDaoJDBC.findAll();
        if (courses.size() == 0){
            courseDaoJDBC.create(new Course("TEST COURSE"));
            courses = courseDaoJDBC.findAll();
        }
        Course courseSrc = courses.get(0);
        Course courseDts = courseDaoJDBC.getCourseById(courseSrc.getCourseId());
        assertEquals(courseSrc.getCourseName(), courseDts.getCourseName());
    }

    @Test
    void updateCourse(){
        List<Course> courses = courseDaoJDBC.findAll();
        if (courses.size() == 0){
            courseDaoJDBC.create(new Course("TEST COURSE"));
            courses = courseDaoJDBC.findAll();
        }
        Course courseSrc = courses.get(0);
        courseSrc.setCourseName(courseSrc.getCourseName() + "_TEST");
        courseDaoJDBC.update(courseSrc);

        Course courseDts = courseDaoJDBC.getCourseById(courseSrc.getCourseId());
        assertEquals(courseSrc.getCourseName(), courseDts.getCourseName());
    }

    @Test
    void deleteCourse(){
        courseDaoJDBC.create(new Course("TEST COURSE"));
        List<Course> courses = courseDaoJDBC.findAll();
        courseDaoJDBC.delete(courses.get(courses.size()-1).getCourseId());
        assertEquals(courses.size()-1, courseDaoJDBC.findAll().size());
    }

    @Test
    void shouldCount(){
        assertNotNull(courseDaoJDBC);
        Integer quantity = courseDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3),quantity);
    }
}