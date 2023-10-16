package com.charapadev.cakenviewapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.charapadev.cakenviewapi.security.filters.BasicAuthenticationFilter;
import com.charapadev.cakenviewapi.security.providers.EmailPassAuthProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityApp {

    @Lazy
    @Autowired
    private BasicAuthenticationFilter basicAuthenticationFilter;

    @Autowired
    private EmailPassAuthProvider emailPassAuthProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(emailPassAuthProvider);

        return builder.build();
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configure(http));
        http.sessionManagement(session -> session.disable());
        http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());

        http.addFilterAt(basicAuthenticationFilter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);

        return http.build();
    }

}
