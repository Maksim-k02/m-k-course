package com.epam.brest.dao;

import com.epam.brest.model.Course;

import java.util.List;

public interface CourseDao {
    List<Course> findAll();
    Integer create(Course course);
    Integer update(Course course);
    Integer delete(Integer courseId);
    Integer count();
}
