package com.charapadev.cakenviewapi.modules.cakes.dtos;

import com.charapadev.cakenviewapi.modules.cakes.entities.CakeRatingData;

public record ShowCakeDTO(
    Long id,
    String name,
    String description,
    String imageUrl,
    CakeRatingData rating
) {

}
