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

    private final Logger LOGGER = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_COURSES="select d.course_id, d.course_name from course d order by d.course_name";
    private final String SQL_CHECK_UNIQUE_COURSE_NAME="select count(d.course_name)" +
            "from course d where lower(d.course_name) = lower(:courseName)";
    private final String SQL_CREATE_COURSE="insert into course(course_name) values(:courseName)";

    public CourseDaoJDBCImpl(DataSource dataSource){
        this.namedParameterJdbcTemplate= new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Course> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_COURSES, new CourseRowMapper());
    }

    @Override
    public Integer create(Course course) {
        LOGGER.debug("Start: create({})", course);

        if (!isCourseUnique((course.getCourseName()))){
            LOGGER.warn("Course with the same name {} already exists", course.getCourseName());
            throw new IllegalArgumentException("Course with the same name already exists in DB");
        }


        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", course.getCourseName().toUpperCase());
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_COURSE, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isCourseUnique(String courseName){
        LOGGER.debug("Check CourseName: {} on unique", courseName);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", courseName);
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_UNIQUE_COURSE_NAME, sqlParameterSource,Integer.class) ==0;
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
