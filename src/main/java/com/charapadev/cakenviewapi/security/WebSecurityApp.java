package com.charapadev.cakenviewapi.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.charapadev.cakenviewapi.security.filters.BasicAuthenticationFilter;
import com.charapadev.cakenviewapi.security.filters.JwtAuthorizationFilter;
import com.charapadev.cakenviewapi.security.providers.EmailPassAuthProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityApp {

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    private EmailPassAuthProvider emailPassAuthProvider;

    @Lazy
    @Autowired
    private BasicAuthenticationFilter basicAuthenticationFilter;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(emailPassAuthProvider);

        return builder.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost", "http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("username", "password", "content-type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception {
        // Default configs for security and conveniences
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint));

        // Filters
        http.addFilterAt(basicAuthenticationFilter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);

        return http.build();
    }

}
