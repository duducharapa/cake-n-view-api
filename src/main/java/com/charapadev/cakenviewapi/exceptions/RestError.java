package com.charapadev.cakenviewapi.exceptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record RestError(
    String error,
    List<FieldDetailError> details
) {}
