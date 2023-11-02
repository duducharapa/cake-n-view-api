package com.charapadev.cakenviewapi.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when the user cannot be found in some step of authentication or authorization.
 */

public class UserNotExistsException extends AuthenticationException {

    public UserNotExistsException(String message) {
        super(message);
    }

}
