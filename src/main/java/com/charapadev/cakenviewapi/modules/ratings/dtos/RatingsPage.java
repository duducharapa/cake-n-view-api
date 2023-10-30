package com.charapadev.cakenviewapi.modules.ratings.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Página contendo informações públicas relacionadas à avaliações")
public class RatingsPage extends PageImpl<ShowRatingDTO> {

    public RatingsPage(List<ShowRatingDTO> content) {
        super(content);
    }

}
