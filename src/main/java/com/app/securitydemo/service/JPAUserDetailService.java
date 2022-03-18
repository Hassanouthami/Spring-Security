package com.app.securitydemo.service;

import com.app.securitydemo.Entity.User;
import com.app.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JPAUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user=userRepository.findByUsername(username);
        User u=user.orElseThrow(()->new UsernameNotFoundException("error"));
        return new SecureUser(u);
    }
}
