package com.charapadev.cakenviewapi.modules.cakes.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados utilizados para cadastrar um novo bolo", requiredProperties = { "name" })
public record CreateCakeDTO(
    @Schema(description = "O nome do bolo", example = "Bolo de morango com creme")
    @NotBlank(message = "The cake's name cannot be blank")
    String name,

    @Schema(description = "Breve descrição sobre o bolo", example = "Um bolo macio com um suave sabor de creme e morango")
    String description,

    @Schema(description = "Link público contendo uma imagem do bolo", example = "http://localhost:8080/imagem.jpg")
    String imageUrl
) {

}
