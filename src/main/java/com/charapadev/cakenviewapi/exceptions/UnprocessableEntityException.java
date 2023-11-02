package com.charapadev.cakenviewapi.exceptions;

/**
 * Exception thrown when the request is well formed but the application cannot proceed with request.
 * For example: when tried to register an user with an email that is already registered, the application cannot proceed
 * because it'll cause duplication on unique data.
 */

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }

}
