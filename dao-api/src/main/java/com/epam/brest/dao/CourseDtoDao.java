package com.epam.brest.dao;

import com.epam.brest.model.dto.CourseDto;

import java.util.List;

/**
 * CourseDto DAO Interface.
 */

public interface CourseDtoDao {

    /**
     * Get all courses with count student by course.
     *
     * @return courses list.
     */
    List<CourseDto> findAllWithCountStudent();
}
