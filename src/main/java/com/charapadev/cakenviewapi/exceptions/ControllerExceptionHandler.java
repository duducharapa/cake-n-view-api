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

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<RestError> handleNotFound(NotFoundException ex, WebRequest request) {
        RestError error = new RestError(ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<RestError> handleNotAuthenticated(AuthenticationException ex, WebRequest request) {
        RestError error = new RestError(ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldDetailError> errors = ex.getFieldErrors().stream().map(fieldError -> {
            return new FieldDetailError(fieldError.getField(), fieldError.getDefaultMessage());
        }).toList();
        RestError error = new RestError("Validation error", errors);

		return handleExceptionInternal(ex, error, headers, status, request);
	}

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralErrors(Exception ex, WebRequest request) {
        RestError error = new RestError("Internal server error", null);

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
