package com.charapadev.cakenviewapi.modules.cakes.dtos;

import com.charapadev.cakenviewapi.modules.cakes.entities.CakeRatingData;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record containing the relevant data to show about any cake.
 */

@Schema(description = "Dados públicos relacionados a um bolo")
public record ShowCakeDTO(
    /**
     * The cake internal identifier.
     */
    @Schema(description = "Identificador do bolo", example = "5")
    Long id,

    /**
     * The visible cake's name.
     */
    @Schema(description = "Nome visível do bolo", example = "Bolo de cenoura com chocolate")
    String name,

    /**
     * A brief description containing details about the cake.
     */
    @Schema(description = "Breve descrição de aspectos do bolo", example = "Bolo macio e suave de cenoura com uma generosa calda de chocolate")
    String description,

    /**
     * The internal or external link about an cake
     */
    @Schema(description = "Link contendo uma imagem ilustrativa do bolo", example = "http://localhost:8080/bolo-cenoura.jpg")
    String imageUrl,

    /**
     * Data related about the currently rating about this cake
     */
    @Schema(description = "Dados gerais sobre avaliações relacionadas ao bolo")
    CakeRatingData rating
) {

}
