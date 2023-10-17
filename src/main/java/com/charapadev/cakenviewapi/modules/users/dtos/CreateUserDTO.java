package com.charapadev.cakenviewapi.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
    @NotBlank(message = "The user must have an email")
    @Email(message = "The user's email must have the correctly format")
    String email,

    @NotBlank(message = "The user must provide a password")
    String password
) {}
