package com.epam.brest.service;

import com.epam.brest.model.dto.CourseDto;

import java.util.List;

public interface CourseDtoService {
    List<CourseDto> findAllWithCountStudent();
}
