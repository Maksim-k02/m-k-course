package com.epam.brest.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {
    private final static String VERSION="0.0.1";

    @GetMapping("/version")
    public String version(){
        return VERSION;
    }
}
