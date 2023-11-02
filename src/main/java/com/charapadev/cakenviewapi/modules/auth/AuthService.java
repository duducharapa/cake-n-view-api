package com.charapadev.cakenviewapi.modules.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.NotFoundException;
import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.cakenviewapi.modules.users.UserRepository;

import lombok.AllArgsConstructor;

/**
 * Service used to perform actions related to authenticated users.
 *
 * It is used when the user is already authenticated. To see some actions related
 * to authentication/authorization, see Security package or {@link TokenService}.
 */

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    /**
     * Extracts the authenticated user data from SecurityContext.
     *
     * @param auth The currently Authentication instance.
     * @return The user extracted from Authentication.
     */
    public User getUserFromAuth(Authentication auth) {
        String email = String.valueOf(auth.getPrincipal());

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Cannot found an user using the email given on context"));
    }

}
