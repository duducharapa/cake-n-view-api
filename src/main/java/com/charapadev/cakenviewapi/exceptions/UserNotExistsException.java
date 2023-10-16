package com.charapadev.cakenviewapi.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotExistsException extends AuthenticationException {

    public UserNotExistsException(String message) {
        super(message);
    }

}
