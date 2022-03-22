package com.example.spring_security.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/hello")
    @Async
    public String hello(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return "hello world !"+authentication.getName();
    }
}
