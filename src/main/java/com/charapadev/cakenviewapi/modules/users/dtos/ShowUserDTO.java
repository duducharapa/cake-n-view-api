package com.charapadev.cakenviewapi.modules.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record containing the public data about any user.
 */

@Schema(description = "Dados públicos de um usuário")
public record ShowUserDTO(
    /**
     * The user identifier.
     */
    @Schema(description = "Identificador interno do usuário", example = "5")
    Long id,

    /**
     * The user public name.
     */
    @Schema(description = "Nome público do usuário", example = "Eduardo Charapa")
    String name
) {}
