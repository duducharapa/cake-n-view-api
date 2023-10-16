package com.charapadev.cakenviewapi.modules.cakes.dtos;

import lombok.Builder;

@Builder
public record UpdateCakeDTO(
    String name,
    String description,
    Double rating
) {
    
}
