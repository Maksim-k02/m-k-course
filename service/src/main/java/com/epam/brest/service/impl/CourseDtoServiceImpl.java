package com.epam.brest.service.impl;


import com.epam.brest.dao.CourseDtoDao;
import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CourseDtoServiceImpl implements CourseDtoService {
    private final CourseDtoDao courseDtoDao;

    public CourseDtoServiceImpl(CourseDtoDao courseDtoDao) {
        this.courseDtoDao = courseDtoDao;
    }

    @Override
    public List<CourseDto> findAllWithCountStudent() {
        return courseDtoDao.findAllWithCountStudent();
    }
}
