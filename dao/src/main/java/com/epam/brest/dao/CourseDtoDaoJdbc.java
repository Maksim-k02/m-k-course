package com.epam.brest.dao;

import com.epam.brest.model.dto.CourseDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;

public class CourseDtoDaoJdbc implements CourseDtoDao{


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String findAllWithCountStudentSql = "SELECT\n" +
            "\td.course_id AS courseId,\n" +
            "\td.course_name AS courseName,\n" +
            "\tcount(e.course_number) AS countStudent\n" +
            "FROM\n" +
            "\tcourse d\n" +
            "LEFT JOIN student e ON\n" +
            "\td.course_id = e.course_id\n" +
            "GROUP BY\n" +
            "\td.course_id,\n" +
            "\td.course_name\n" +
            "ORDER BY\n" +
            "\tcourse_name";


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
