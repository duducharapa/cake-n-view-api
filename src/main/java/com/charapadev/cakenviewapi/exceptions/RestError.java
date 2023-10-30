package com.charapadev.cakenviewapi.exceptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados relacionados ao erro ocorrido em alguma requisição", requiredProperties = { "error" })
@JsonInclude(Include.NON_NULL)
public record RestError(
    @Schema(description = "Descrição relacionada ao erro ocorrido", example = "User not found")
    String error,

    @Schema(description = "Lista de erros ao validar o corpo de uma requisição")
    List<FieldDetailError> details
) {}
