package com.budlee.apiworkshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkshopController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

}