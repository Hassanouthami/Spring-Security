package com.example.spring_security.security.provider;

import com.example.spring_security.security.authentication.TokenAuthentication;
import com.example.spring_security.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token =authentication.getName();
        boolean exist =tokenManager.contain(token);
        if(exist){
              return new TokenAuthentication(token,null, List.of());
        }
        throw  new BadCredentialsException(":( token provides class  :   "+token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
