package com.epam.brest.dao;

import com.epam.brest.model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger logger = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_COURSES="select d.course_id, d.course_name from course d order by d.course_name";
    private final String SQL_CREATE_COURSE="insert into course(course_name) values(:courseName)";

    public CourseDaoJDBCImpl(DataSource dataSource){
        this.namedParameterJdbcTemplate= new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Course> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_COURSES, new CourseRowMapper());
    }

    @Override
    public Integer create(Course course) {
        logger.debug("Start: create({})", course);
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
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            return course;
        }
    }
}
