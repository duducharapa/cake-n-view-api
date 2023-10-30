package com.charapadev.cakenviewapi.modules.cakes.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Dados editáveis de um bolo")
@Builder
public record UpdateCakeDTO(
    @Schema(description = "Novo nome do bolo")
    String name,

    @Schema(description = "Nova descrição do bolo")
    String description,

    @Schema(description = "Nova nota geral relacionada ao bolo")
    Double rating
) {

}
