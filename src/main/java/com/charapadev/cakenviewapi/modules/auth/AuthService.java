package com.charapadev.cakenviewapi.modules.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.NotFoundException;
import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.cakenviewapi.modules.users.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    public User getUserFromAuth(Authentication auth) {
        String email = String.valueOf(auth.getPrincipal());

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Cannot found an user using the email given on context"));
    }

}
