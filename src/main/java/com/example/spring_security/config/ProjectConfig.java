package com.example.spring_security.config;


import com.example.spring_security.security.filter.TokenAuthenticationFilter;
import com.example.spring_security.security.filter.UsernamePasswordAuthFilter;
import com.example.spring_security.security.provider.OtpAuthenticationProvider;
import com.example.spring_security.security.provider.TokenAuthenticationProvider;
import com.example.spring_security.security.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    @Lazy
    OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    @Lazy
    private UsernamePasswordAuthFilter usernamePasswordAuthFilter;
    @Autowired
    @Lazy
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @Autowired
    @Lazy
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(usernamePasswordAuthFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(tokenAuthenticationFilter,BasicAuthenticationFilter.class);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public InitializingBean initializingBean(){
        return ()->{
            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }*/

}
