package com.epam.brest.dao;

import com.epam.brest.model.dto.CourseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.util.List;

public class CourseDtoDaoJdbc implements CourseDtoDao{


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${findAllWithCountStudentSql}")
    private String findAllWithCountStudentSql;


    public CourseDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<CourseDto> findAllWithCountStudent() {
        List<CourseDto> courses = namedParameterJdbcTemplate.query(findAllWithCountStudentSql,
                BeanPropertyRowMapper.newInstance(CourseDto.class));
        return courses;

    }
}
