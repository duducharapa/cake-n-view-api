package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the cakes and also the pies available to be rated on application.
 */

@Entity
@Table(name = "cakes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cake implements Serializable {

    /**
     * Internal cake identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Visible cake's name.
     *
     * It is only used as an preview for users about the flavors or anything else related.
     */
    @Column(nullable = false)
    private String name;

    /**
     * An brief description about the cake.
     *
     * It can be used to inform users about some cake details like flavors, consistency and much more.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * General data about the ratings related to this cake.
     *
     * It is used for filterings and classify cakes based on it.
     */
    @Embedded
    @Builder.Default
    private CakeRatingData rating = new CakeRatingData(0.0, 0);

    /**
     * The principal image vinculed to cake.
     *
     * It can be an external URL from network or an link provided from an internal file implementation.
     */
    @Column
    @Builder.Default
    private String imageUrl = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cake)) return false;
        Cake cake = (Cake) o;
        return Objects.equals(getId(), cake.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
