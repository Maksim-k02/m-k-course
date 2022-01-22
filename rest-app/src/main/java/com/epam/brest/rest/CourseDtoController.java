package com.epam.brest.rest;

import com.epam.brest.dao.CourseDaoJDBCImpl;
import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CourseDtoController {
    private static final Logger logger = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private final CourseDtoService courseDtoService;

    public CourseDtoController(CourseDtoService courseDtoService) {
        this.courseDtoService = courseDtoService;
    }



    @GetMapping(value = "/course_dtos")
    public final Collection<CourseDto> courseDtos() {

        logger.debug("courseDtos()");
        return courseDtoService.findAllWithCountStudent();
    }
}
