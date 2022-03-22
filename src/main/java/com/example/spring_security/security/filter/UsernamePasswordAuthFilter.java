package com.example.spring_security.security.filter;


import com.example.spring_security.entity.Otp;
import com.example.spring_security.repository.OtpRepository;
import com.example.spring_security.security.authentication.OtpAuthentication;
import com.example.spring_security.security.authentication.UsernamePasswordAuthentication;
import com.example.spring_security.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var username=request.getHeader("username");
        var password=request.getHeader("password");
        var otp=request.getHeader("otp");
        if(otp==null){
            Authentication auth=new UsernamePasswordAuthentication(username,password);
            auth = authenticationManager.authenticate(auth);
            String code=String.valueOf(new Random().nextInt(9000)+1000);
            Otp otpEntity=new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        }
        else{
            Authentication auth=new OtpAuthentication(username,password);
           auth = authenticationManager.authenticate(auth);
           var token =String.valueOf(UUID.randomUUID());
           tokenManager.addToken(token);
            response.setHeader("authorization", token);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
