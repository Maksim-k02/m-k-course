package com.epam.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class CourseNotFoundException extends EmptyResultDataAccessException {
    public CourseNotFoundException(Integer id) {
        super("Course not found for id: " + id, 1);
    }
}
