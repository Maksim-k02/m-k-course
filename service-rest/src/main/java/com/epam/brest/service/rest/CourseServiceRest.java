package com.epam.brest.service.rest;

import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CourseServiceRest implements CourseService {

    private final Logger logger = LogManager.getLogger(CourseDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public CourseServiceRest() {
    }

    public CourseServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Course> findAll() {
        logger.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Course>) responseEntity.getBody();
    }

    @Override
    public Course getCourseById(Integer courseId) {
        logger.debug("findById({})", courseId);
        ResponseEntity<Course> responseEntity =
                restTemplate.getForEntity(url + "/" + courseId, Course.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Course course) {
        logger.debug("create({})", course);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, course, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Course course) {

        logger.debug("update({})", course);
        // restTemplate.put(url, course);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Course> entity = new HttpEntity<>(course, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer courseId) {
        logger.debug("delete({})", courseId);
        //restTemplate.delete(url + "/" + courseId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Course> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + courseId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count" , Integer.class);
        return responseEntity.getBody();
    }
}
