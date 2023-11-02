package com.charapadev.cakenviewapi.modules.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record containing the necessary data to perform the user registration.
 */

@Schema(description = "Dados essenciais para a criação de um novo usuário")
public record CreateUserDTO(
    /**
     * The user email address.
     */
    @Schema(description = "Email vinculado a conta que será criada", example = "teste@gmail.com")
    @NotBlank(message = "The user must have an email")
    @Email(message = "The user's email must have the correctly format")
    String email,

    /**
     * The user public email.
     */
    @Schema(description = "Nome a ser exibido do usuário", example = "Eduardo Charapa")
    @NotBlank(message = "The user must have a name")
    String name,

    /**
     * The plain user password.
     */
    @Schema(description = "Senha para autenticação do usuário", example = "Teste123")
    @NotBlank(message = "The user must provide a password")
    String password
) {}
