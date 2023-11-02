package com.charapadev.cakenviewapi.modules.cakes.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the rating data related to an specific cake.
 *
 * It can be used only for visualizations or checks if some cake satisfy the users or not.
 */

@Schema(description = "Dados gerais relacionados à avaliações de um bolo específico")
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CakeRatingData {

    /**
     * The total average of ratings posted about the cake.
     */
    @Schema(description = "Média geral relacionada às avaliações enviadas sobre o bolo", example = "4.5")
    @Column(name = "average_rating")
    private Double average;

    /**
     * The quantity of ratings posted.
     */
    @Schema(description = "Total de avaliações adicionadas a este bolo", example = "2")
    @Column(name = "total_ratings")
    private Integer quantity;

    /**
     * Increments a new rating on quantity counter.
     */
    public void incrementRatings() {
        this.quantity++;
    }

}
