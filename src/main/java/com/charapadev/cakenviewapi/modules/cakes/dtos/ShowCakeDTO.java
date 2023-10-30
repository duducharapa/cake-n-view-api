package com.charapadev.cakenviewapi.modules.cakes.dtos;

import com.charapadev.cakenviewapi.modules.cakes.entities.CakeRatingData;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados públicos relacionados a um bolo")
public record ShowCakeDTO(
    @Schema(description = "Identificador do bolo", example = "5")
    Long id,

    @Schema(description = "Nome ", example = "Bolo de cenoura com chocolate")
    String name,

    @Schema(description = "Breve descrição de aspectos do bolo", example = "Bolo macio e suave de cenoura com uma generosa calda de chocolate")
    String description,

    @Schema(description = "Link contendo uma imagem ilustrativa do bolo", example = "http://localhost:8080/bolo-cenoura.jpg")
    String imageUrl,

    @Schema(description = "Dados gerais sobre avaliações relacionadas ao bolo")
    CakeRatingData rating
) {

}
