package com.charapadev.cakenviewapi.modules.ratings.dtos;

import java.sql.Timestamp;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record containing the relevant data about any rating.
 */

@Schema(description = "Dados públicos relacionados a avaliações feitas sobre algum bolo", requiredProperties = { "id", "number", "createdAt", "user" })
public record ShowRatingDTO(
    /**
     * Rating identifier.
     */
    @Schema(description = "Identificador interno da avaliação", example = "1")
    Long id,

    /**
     * The value given on rating, from 0 to 5.
     */
    @Schema(description = "Valor da avaliação dada ao bolo pelo usuário", example = "5")
    Double number,

    /**
     * A possibly brief description sent by user.
     */
    @Schema(description = "Comentário opcional relacionado à avaliação", example = "Este bolo é SURREAL!")
    String comment,

    /**
     * The date when the rating was posted.
     */
    @Schema(description = "Data na qual o comentário foi adicionado", example = "2023-10-30T07:49:17.163Z")
    Timestamp createdAt,

    /**
     * The evaluator user.
     */
    @Schema(description = "Usuário que realizou a avaliação")
    ShowUserDTO user
) {

}
