package com.charapadev.cakenviewapi.exceptions;

public record FieldDetailError(
    String field,
    String message
) {}
