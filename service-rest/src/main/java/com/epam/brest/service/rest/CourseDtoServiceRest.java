package com.epam.brest.service.rest;

import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CourseDtoServiceRest implements CourseDtoService {

    private final Logger LOGGER = LogManager.getLogger(CourseDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public CourseDtoServiceRest(){
        // default constructor
    }
    public CourseDtoServiceRest(String url, RestTemplate restTemplate){
        this();
        this.url=url;
        this.restTemplate=restTemplate;
    }

    @Override
    public List<CourseDto> findAllWithCountStudent() {
        LOGGER.debug("findAllWithCountStudent()");;
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<CourseDto>) responseEntity.getBody();
    }
}
