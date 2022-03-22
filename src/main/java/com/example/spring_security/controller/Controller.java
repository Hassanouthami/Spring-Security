package com.example.spring_security.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello(){
        Runnable runnable=()-> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getName());
        };
        ExecutorService service= Executors.newSingleThreadExecutor();
        service.submit(runnable);
        service.shutdown();
        return "hello world !";
    }
}
