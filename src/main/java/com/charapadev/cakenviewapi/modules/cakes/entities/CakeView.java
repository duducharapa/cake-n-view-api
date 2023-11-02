package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the visualizations of cakes.
 *
 * It can be used to classify them are trending or not and get insights about what the users like more.
 */

@Entity
@Table(name = "views")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CakeView implements Serializable {

    /**
     * Visualization internal identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * The quantity of times the cake was visited.
     */
    @Column
    @Builder.Default
    private int views = 0;

    /**
     * The cake itself that is being visited.
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Cake cake;

    /**
     * Adds a new visualization on views counter.
     */
    public void incrementView() {
        this.views++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CakeView)) return false;
        CakeView cakeView = (CakeView) o;
        return Objects.equals(getId(), cakeView.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
