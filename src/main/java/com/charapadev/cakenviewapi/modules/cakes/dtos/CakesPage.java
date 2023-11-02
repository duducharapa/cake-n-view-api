package com.charapadev.cakenviewapi.modules.cakes.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record used to represent the Page implementation about the cake listing.
 */

@Schema(description = "Página contendo informações públicas relacionadas à bolos e tortas")
public class CakesPage extends PageImpl<ShowCakeDTO> {

    public CakesPage(List<ShowCakeDTO> content) {
        super(content);
    }

}
