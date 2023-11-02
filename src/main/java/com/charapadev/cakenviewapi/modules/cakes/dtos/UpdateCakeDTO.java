package com.charapadev.cakenviewapi.modules.cakes.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Record containg the editable data available for any cake.
 */

@Schema(description = "Dados editáveis de um bolo")
@Builder
public record UpdateCakeDTO(
    /**
     * The new visible cake's name.
     */
    @Schema(description = "Novo nome do bolo")
    String name,

    /**
     * A new description for the cake.
     */
    @Schema(description = "Nova descrição do bolo")
    String description,

    /**
     * The new average rating for this cake.
     */
    @Schema(description = "Nova nota geral relacionada ao bolo")
    Double rating
) {

}
