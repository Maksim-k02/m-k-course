package com.epam.brest.service;


import com.epam.brest.model.Course;

public interface CourseService {
    Integer create(Course course);

    Integer count();

}
