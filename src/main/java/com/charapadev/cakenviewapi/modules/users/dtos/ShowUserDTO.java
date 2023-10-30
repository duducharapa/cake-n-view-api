package com.charapadev.cakenviewapi.modules.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados públicos de um usuário")
public record ShowUserDTO(
    @Schema(description = "Identificador interno do usuário", example = "5")
    Long id,

    @Schema(description = "Nome público do usuário", example = "Eduardo Charapa")
    String name
) {}
