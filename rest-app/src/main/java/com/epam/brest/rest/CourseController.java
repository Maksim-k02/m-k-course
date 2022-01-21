package com.epam.brest.rest;


import com.epam.brest.dao.CourseDaoJDBCImpl;
import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
public class CourseController {

    private static final Logger logger = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/courses")
    public final Collection<Course> courses() {

        logger.debug("courses()");
        return courseService.findAll();
    }

    @GetMapping(value = "/courses/{id}")
    public final Course getCourseById(@PathVariable Integer id){

        logger.debug("getCourseById({})", id);
        Course course = courseService.getCourseById(id);
        return course;
    }

    @PostMapping(path = "/courses", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createCourse(@RequestBody Course course) {

        logger.debug("createCourse({})", course);
        Integer id = courseService.create(course);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/courses", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateCourse(@RequestBody Course course) {

        logger.debug("updateCourse({})", course);
        int result = courseService.update(course);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/courses/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteCourse(@PathVariable Integer id) {

        int result = courseService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
