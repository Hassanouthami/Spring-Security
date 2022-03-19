package com.app.securitydemo.controller;


import com.app.securitydemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private JdbcUserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello(){
        var nom="hassan";
        return "hello "+nom;
    }

    @PostMapping("newUser")
    public void create(@RequestBody User user){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsManager.createUser(user);
    }
}
