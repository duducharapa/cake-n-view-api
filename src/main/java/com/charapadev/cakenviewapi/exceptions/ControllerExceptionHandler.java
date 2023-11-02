package com.charapadev.cakenviewapi.exceptions;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller used to intercept some exceptions thrown by application.
 * Based on each exception, the application produces differents HTTP responses with various codes.
 */

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception thrown when some essential resource cannot be found.
     *
     * @param ex The NotFoundException instance.
     * @param request The WebRequest object.
     * @return The response entity with 404 HTTP code and error details.
     */
    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<RestError> handleNotFound(NotFoundException ex, WebRequest request) {
        RestError error = new RestError(ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Exception thrown when the user is not authorized to do some actions on application.
     *
     * @param ex The AuthenticationException instance.
     * @param request The WebRequest object.
     * @return The response entity with 403 HTTP code and error details.
     */
    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<RestError> handleNotAuthenticated(AuthenticationException ex, WebRequest request) {
        RestError error = new RestError(ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /**
     * Exception thrown when everything is well formed, but the application cannot perform the desired action, generally because
     * some unique data already exists on application.
     *
     * @param ex The UnprocessableEntityException instance.
     * @param request The WebRequest object.
     * @return The response entity with 422 HTTP code and error details.
     */
    @ExceptionHandler(value = { UnprocessableEntityException.class })
    protected ResponseEntity<RestError> handleExistanceErrpr(UnprocessableEntityException ex, WebRequest request) {
        RestError error = new RestError(ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    /**
     * Exception thrown when occurs some error validating body requests.
     *
     * This method will produce the 400 HTTP code with the error details on body.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldDetailError> errors = ex.getFieldErrors().stream().map(fieldError -> {
            return new FieldDetailError(fieldError.getField(), fieldError.getDefaultMessage());
        }).toList();
        RestError error = new RestError("Validation error", errors);

		return handleExceptionInternal(ex, error, headers, status, request);
	}

    /**
     * Generic handler for any exception that not fits on previous handlers.
     *
     * @param ex The exception instance.
     * @param request The WebRequest object.
     * @return The response entity with 500 HTTP message and static message "Internal server error"
     */
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralErrors(Exception ex, WebRequest request) {
        RestError error = new RestError("Internal server error", null);

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
