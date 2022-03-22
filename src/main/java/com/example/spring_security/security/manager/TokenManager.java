package com.example.spring_security.security.manager;

import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Set;

@Component
public class TokenManager {
    private Set<String> tokens=new HashSet<String>();

    public void addToken(String token){
        tokens.add(token);
    }

    public boolean contain(String token){
        return tokens.contains(token);
    }

}
