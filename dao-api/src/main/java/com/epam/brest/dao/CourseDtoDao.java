package com.epam.brest.dao;

import com.epam.brest.model.dto.CourseDto;

import java.util.List;

/**
 * DepartmentDto DAO Interface.
 */

public interface CourseDtoDao {

    /**
     * Get all departments with avg salary by department.
     *
     * @return departments list.
     */
    List<CourseDto> findAllWithCountStudent();
}
