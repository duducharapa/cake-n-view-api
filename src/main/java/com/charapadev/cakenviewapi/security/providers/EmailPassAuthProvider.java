package com.charapadev.cakenviewapi.security.providers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.security.authentications.EmailPassAuthentication;
import com.charapadev.cakenviewapi.security.filters.BasicAuthenticationFilter;
import com.charapadev.cakenviewapi.security.userdetails.CustomUserDetails;
import com.charapadev.cakenviewapi.security.userdetails.UserDetailsServiceImpl;

/**
 * Provider to validate EmailPassAuthentication added on {@link BasicAuthenticationFilter}.
 * If the email is registered and password matches, proceed with authentication.
 */

@Component
public class EmailPassAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Credentials not match");
        }

        EmailPassAuthentication auth = new EmailPassAuthentication(
            userDetails.getUsername(),
            userDetails.getPassword(),
            List.of(new SimpleGrantedAuthority("user"))
        );
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailPassAuthentication.class.isAssignableFrom(authentication);
    }

}
