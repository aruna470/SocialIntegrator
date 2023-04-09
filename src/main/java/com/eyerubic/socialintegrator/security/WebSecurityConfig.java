package com.eyerubic.socialintegrator.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class authenticates all the API requests before reaching to intented controller.
 * Perform only basic authentication. Authentication credentials are defined under property file 
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthorizationFilter jwtFilter = new JWTAuthorizationFilter();
        jwtFilter.setJwtSecret(jwtSecret);

        http.cors()
            .and()
            .csrf().disable() // NOSONAR
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/user/signin", "/user/signup", "/user/verifyCode", 
                "/user/forgotPassword", "/user/resetPassword", "/callback/whatsapp").permitAll()
            //.antMatchers(HttpMethod.GET, "/ratingCategory/byWidgetCode/*").permitAll()
            .anyRequest().authenticated();
    }
}
