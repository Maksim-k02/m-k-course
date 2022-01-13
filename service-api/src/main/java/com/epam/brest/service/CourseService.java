package com.epam.brest.service;


import com.epam.brest.model.Course;

public interface CourseService {
    Course getCourseById(Integer courseId);
    Integer create(Course course);
    Integer update(Course course);
    Integer delete(Integer courseId);

    Integer count();

}
