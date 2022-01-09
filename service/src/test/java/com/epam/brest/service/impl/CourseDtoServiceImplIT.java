package com.epam.brest.service.impl;


import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
public class CourseDtoServiceImplIT {

    @Autowired
    CourseDtoService courseDtoService;

    @Test
    public void shouldFindAllWithCountStudent() {
        List<CourseDto> courses = courseDtoService.findAllWithCountStudent();
        assertNotNull(courses);
        assertTrue(courses.size() > 0);
        assertTrue(courses.get(0).getCountStudent().intValue() > 0);
    }
}
