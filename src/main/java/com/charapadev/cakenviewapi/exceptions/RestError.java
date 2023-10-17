package com.charapadev.cakenviewapi.exceptions;

import java.util.List;

public record RestError(
    String error,
    List<FieldDetailError> details
) {}
