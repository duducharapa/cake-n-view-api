package com.charapadev.cakenviewapi.modules.ratings.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RateCakeDTO(
    @NotNull(message = "The rating must be linked to some cake")
    Long cakeId,

    @Min(value = 0, message = "The rating must have an note greater or equals to 0")
    @Max(value = 5, message = "The rating must have an note lower than 5")
    Double number,

    String comment
) {}
