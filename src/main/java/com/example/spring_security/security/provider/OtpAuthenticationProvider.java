package com.example.spring_security.security.provider;

import com.example.spring_security.repository.OtpRepository;
import com.example.spring_security.security.authentication.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username=authentication.getName();
        var otp=(String)authentication.getCredentials();
        var otpEntity=otpRepository.findOtpByUsername(username);
        if(otpEntity.isPresent()){
            return new OtpAuthentication(username,otp, List.of(()->"read"));
        }
        throw new BadCredentialsException(":(");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
