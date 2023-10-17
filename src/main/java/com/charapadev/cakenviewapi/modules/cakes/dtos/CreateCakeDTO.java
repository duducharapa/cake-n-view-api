package com.charapadev.cakenviewapi.modules.cakes.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCakeDTO(
    @NotBlank(message = "The cake's name cannot be blank")
    String name,

    String description,
    String imageUrl
) {

}
