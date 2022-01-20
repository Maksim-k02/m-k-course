package com.epam.brest.service;


import com.epam.brest.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course getCourseById(Integer courseId);
    Integer create(Course course);
    Integer update(Course course);
    Integer delete(Integer courseId);

    Integer count();

}
