package com.notahmed.springsecurityldap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Home {

    @GetMapping("/")
    public String home() {
        return "<h1>Home Page</h1>";
    }
}
