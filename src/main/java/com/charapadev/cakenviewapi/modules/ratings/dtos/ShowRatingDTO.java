package com.charapadev.cakenviewapi.modules.ratings.dtos;

import java.sql.Timestamp;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados públicos relacionados a avaliações feitas sobre algum bolo", requiredProperties = { "id", "number", "createdAt", "user" })
public record ShowRatingDTO(
    @Schema(description = "Identificador interno da avaliação", example = "1")
    Long id,

    @Schema(description = "Valor da avaliação dada ao bolo pelo usuário", example = "5")
    Double number,

    @Schema(description = "Comentário opcional relacionado à avaliação", example = "Este bolo é SURREAL!")
    String comment,

    @Schema(description = "Data na qual o comentário foi adicionado", example = "2023-10-30T07:49:17.163Z")
    Timestamp createdAt,

    @Schema(description = "Usuário que realizou a avaliação")
    ShowUserDTO user
) {

}
