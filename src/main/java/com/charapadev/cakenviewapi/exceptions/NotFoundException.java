package com.charapadev.cakenviewapi.exceptions;

/**
 * Exception thrown when some essential resource cannot be found to perform some action.
 *
 * For example: the user with ID 6 wanna rate some cake. If he not exists by this ID, then we cannot persist the rating given.
 */

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
