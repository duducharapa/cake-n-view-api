package com.charapadev.cakenviewapi.security.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charapadev.cakenviewapi.modules.auth.TokenService;
import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.cakenviewapi.security.authentications.EmailPassAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String TOKEN_PREFIX = "Bearer ";
        String headerToken = request.getHeader("Authorization").substring(TOKEN_PREFIX.length());

        User tokenOwner = tokenService.decode(headerToken);
        EmailPassAuthentication auth = new EmailPassAuthentication(
            tokenOwner.getEmail(), null, List.of(new SimpleGrantedAuthority("user"))
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        boolean isLoginPath = path.equals("/users") && request.getMethod().equals("POST");
        return isLoginPath || path.equals("/login");
    }

}
