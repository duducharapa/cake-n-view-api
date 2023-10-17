package com.charapadev.cakenviewapi.modules.cakes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CakeRatingData {

    @Column(name = "average_rating")
    private Double average;

    @Column(name = "total_ratings")
    private Integer quantity;

    public void incrementRatings() {
        this.quantity++;
    }

}
