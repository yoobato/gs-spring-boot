package com.yoobato.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SampleController {

    @GetMapping
    public String index() {
        return "This is Sample Spring Boot project!";
    }
}
