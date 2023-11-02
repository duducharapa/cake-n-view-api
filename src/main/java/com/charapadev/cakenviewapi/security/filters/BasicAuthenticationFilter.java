package com.charapadev.cakenviewapi.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charapadev.cakenviewapi.modules.auth.SuccessLoginDTO;
import com.charapadev.cakenviewapi.modules.auth.TokenService;
import com.charapadev.cakenviewapi.security.authentications.EmailPassAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter used on login endpoint logic.
 *
 * It receives the username and password given on request headers and authenticates it on AuthenticationManager.
 * If everything is ok, the response will have an JWT token for subsequent identifications.
 */

@Component
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = request.getHeader("username");
        String password = request.getHeader("password");

        EmailPassAuthentication auth = new EmailPassAuthentication(email, password);
        authenticationManager.authenticate(auth);

        String token = tokenService.generate(email);
        SuccessLoginDTO loginDTO = new SuccessLoginDTO(token);

        // Mounting response for authenticated user
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(201);
        new ObjectMapper().writeValue(response.getOutputStream(), loginDTO);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/login");
	}

}
