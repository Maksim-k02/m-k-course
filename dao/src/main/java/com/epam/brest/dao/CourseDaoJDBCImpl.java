package com.epam.brest.dao;

import com.epam.brest.model.Course;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDaoJDBCImpl implements CourseDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_COURSE="select d.courseId, d.courseName from course d order by d.courseName";
    private final String SQL_CREATE_COURSE = "insert into course(courseName) values(:courseName)";

    public CourseDaoJDBCImpl(DataSource dataSource){
        this.namedParameterJdbcTemplate= new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Course> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_COURSE, new CourseRowMapper());
    }

    @Override
    public Integer create(Course course) {

        //TODO: isCourseUnique throw new IllegalArgumentException

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", course.getCourseName().toUpperCase());
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_COURSE, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Course course) {
        return null;
    }

    @Override
    public Integer delete(Integer courseId) {
        return null;
    }

    private class CourseRowMapper implements RowMapper<Course>{

        @Override
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("courseId"));
            course.setCourseName(resultSet.getString("courseName"));
            return course;
        }
    }
}
