package com.charapadev.cakenviewapi.modules.ratings.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Record containing the data used to post an rating about any cake.
 */

@Schema(description = "Dados utilizados para adicionar uma nova avaliação", requiredProperties = { "cakeId", "number" })
public record RateCakeDTO(
    /**
     * The cake internal identifier.
     */
    @Schema(description = "Identificador do bolo a ser avaliado", example = "5")
    @NotNull(message = "The rating must be linked to some cake")
    Long cakeId,

    /**
     * The value passed by user for the cake, from 0 to 5.
     */
    @Schema(description = "Grau de satisfação, de 0 a 5, do usuário em relação a um bolo", example = "3")
    @Min(value = 0, message = "The rating must have an note greater or equals to 0")
    @Max(value = 5, message = "The rating must have an note lower than 5")
    Double number,

    /**
     * An optional and brief text containing details about what made the user choose this value.
     */
    @Schema(description = "Breve descrição relacionada a certa avaliação de um usuário", example = "Bolo com um bom aspecto e sabor")
    String comment
) {}
