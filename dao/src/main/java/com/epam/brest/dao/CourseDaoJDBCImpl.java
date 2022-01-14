package com.epam.brest.dao;

import com.epam.brest.model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CourseDaoJDBCImpl implements CourseDao{

    private final Logger LOGGER = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SELECT_COUNT_FROM_COURSE}")
    public String sqlCourseCount;

    @Value("${SQL_COURSE_BY_ID}")
    private String sqlGetCourseById;

    @Value("${SQL_ALL_COURSES}")
    private String sqlGetAllCourse;

    @Value("${SQL_CHECK_UNIQUE_COURSE_NAME}")
    private String sqlCheckUniqueCourseName;

    @Value("${SQL_CREATE_COURSE}")
    private String sqlCreateCourse;

    @Value("${SQL_UPDATE_COURSE_NAME}")
    private String sqlUpdateCourseName;

    @Value("${SQL_DELETE_COURSE_BY_ID}")
    private String sqlDeleteCourseById;

//    public CourseDaoJDBCImpl(DataSource dataSource){
//        this.namedParameterJdbcTemplate= new NamedParameterJdbcTemplate(dataSource);
//    }

    public CourseDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }

    @Override
    public List<Course> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(sqlGetAllCourse, new CourseRowMapper());
    }

    @Override
    public Course getCourseById(Integer courseId) {
        LOGGER.debug("Get course by id = {}", courseId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseId", courseId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetCourseById, sqlParameterSource, new CourseRowMapper());
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
        namedParameterJdbcTemplate.update(sqlCreateCourse, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isCourseUnique(String courseName){
        LOGGER.debug("Check CourseName: {} on unique", courseName);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", courseName);
        return namedParameterJdbcTemplate.queryForObject(sqlCheckUniqueCourseName, sqlParameterSource,Integer.class) ==0;
    }

    @Override
    public Integer update(Course course) {
        LOGGER.debug("Update course: {}", course);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseName", course.getCourseName())
                .addValue("courseId",course.getCourseId());
        return namedParameterJdbcTemplate.update(sqlUpdateCourseName, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer courseId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("courseId", courseId);
        return namedParameterJdbcTemplate.update(sqlDeleteCourseById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        LOGGER.debug("count()");
        return namedParameterJdbcTemplate.queryForObject(sqlCourseCount, new MapSqlParameterSource(), Integer.class);
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
