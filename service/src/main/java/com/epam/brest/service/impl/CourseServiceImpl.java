package com.epam.brest.service.impl;

import com.epam.brest.dao.CourseDao;
import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import com.epam.brest.service.exceptions.CourseNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        logger.trace("findAll()");
        return courseDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(Integer courseId) {
        logger.debug("Get course by id = {}", courseId);
        try {
            return this.courseDao.getCourseById(courseId);
        } catch (EmptyResultDataAccessException e) {
            throw new CourseNotFoundException(courseId);
        }
    }

    @Override
    @Transactional
    public Integer create(Course course) {
        logger.debug("create({})", course);
        return this.courseDao.create(course);
    }

    @Override
    @Transactional
    public Integer update(Course course) {
        logger.debug("update({})", course);
        return this.courseDao.update(course);
    }

    @Override
    @Transactional
    public Integer delete(Integer courseId) {
        logger.debug("delete course with id = {}", courseId);
        return this.courseDao.delete(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.courseDao.count();
    }
}
