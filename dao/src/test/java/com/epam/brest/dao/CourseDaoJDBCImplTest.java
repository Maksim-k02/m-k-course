package com.epam.brest.dao;

import com.epam.brest.model.Course;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CourseDaoJDBCImplTest {

    @InjectMocks
    private CourseDaoJDBCImpl courseDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void findAll() {

        Course course = new Course();
        List<Course> list = Collections.singletonList(course);

        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Course>>any())).thenReturn(list);

        List<Course> result = courseDaoJDBC.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(course, result.get(0));
    }

    @Test
    public void getCourseById(){
        int id = 0;

        Course course = new Course();

        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Course>>any()))
                .thenReturn(course);

        Course result = courseDaoJDBC.getCourseById(id);

        Assertions.assertNotNull(result);
        Assertions.assertSame(course, result);
    }
}
