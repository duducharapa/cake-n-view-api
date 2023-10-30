package com.charapadev.cakenviewapi.modules.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados essenciais para a criação de um novo usuário")
public record CreateUserDTO(
    @Schema(description = "Email vinculado a conta que será criada", example = "teste@gmail.com")
    @NotBlank(message = "The user must have an email")
    @Email(message = "The user's email must have the correctly format")
    String email,

    @Schema(description = "Nome a ser exibido do usuário", example = "Eduardo Charapa")
    @NotBlank(message = "The user must have a name")
    String name,

    @Schema(description = "Senha para autenticação do usuário", example = "Teste123")
    @NotBlank(message = "The user must provide a password")
    String password
) {}
