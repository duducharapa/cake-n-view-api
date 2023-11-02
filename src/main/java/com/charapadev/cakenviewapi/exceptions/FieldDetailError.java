package com.charapadev.cakenviewapi.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record containing the field data error thrown on request body validation.
 */

@Schema(description = "Dados relacionados a erros lançados ao validar o corpo de requisições")
public record FieldDetailError(
    /**
     * The field where the validation failed.
     */
    @Schema(description = "Nome do campo enviado na requisição que ocasionou o erro", defaultValue = "email")
    String field,

    /**
     * Description about what makes the validation on this field fails.
     */
    @Schema(description = "Informações sobre o erro ocasionado", defaultValue = "The user's email must have the correctly format")
    String message
) {}
