package com.epam.brest.service.impl;

import com.epam.brest.dao.CourseDao;
import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

    private final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    @Transactional
    public Integer create(Course course) {
        logger.debug("create({})", course);
        return this.courseDao.create(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.courseDao.count();
    }
}
