package com.example.spring_security.security.provider;

import com.example.spring_security.security.authentication.UsernamePasswordAuthentication;
import com.example.spring_security.service.JpaUserDetailService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JpaUserDetailService jpaUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username =authentication.getName();
        var password=(String) authentication.getCredentials();
        UserDetails user=jpaUserDetailService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuthentication(username,password,user.getAuthorities());
        }
        throw new BadCredentialsException(":(");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
