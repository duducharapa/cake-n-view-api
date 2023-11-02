package com.charapadev.cakenviewapi.modules.ratings.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record used to represent the Page implementation about the ratings listing.
 */

@Schema(description = "Página contendo informações públicas relacionadas à avaliações")
public class RatingsPage extends PageImpl<ShowRatingDTO> {

    public RatingsPage(List<ShowRatingDTO> content) {
        super(content);
    }

}
