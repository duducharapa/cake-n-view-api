package com.charapadev.cakenviewapi.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados relacionados a erros lançados ao validar o corpo de requisições")
public record FieldDetailError(
    @Schema(description = "Nome do campo enviado na requisição que ocasionou o erro", defaultValue = "email")
    String field,

    @Schema(description = "Informações sobre o erro ocasionado", defaultValue = "The user's email must have the correctly format")
    String message
) {}
