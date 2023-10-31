package com.charapadev.cakenviewapi.modules.cakes.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "Dados públicos relacionados ao bolo escolhido no dia", requiredMode = RequiredMode.REQUIRED)
public record ShowDailyCakeDTO(
    @Schema(description = "Dados públicos do bolo escolhido")
    ShowCakeDTO cake,

    @Schema(description = "Data limite onde o bolo estará disponível como bolo do dia", example = "2023-10-30T13:04:25.828Z")
    String expiresAt
) {}
