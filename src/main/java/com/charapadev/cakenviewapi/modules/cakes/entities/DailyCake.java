package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/**
 * Represents the currently and previous cakes chosen as daily.
 *
 * Daily cakes are suggestion of cakes given to users to estimulate the visualization frequently.
 */

@Entity
@Table(name = "daily_cakes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyCake implements Serializable {

    /**
     * Daily cake internal identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Chosen daily cake.
     */
    @OneToOne(optional = false)
    private Cake cake;

    /**
     * If the cake is current in the given day.
     *
     * The application only permits once cake to be daily per time.
     */
    @Column
    @Builder.Default
    private boolean current = true;

    /**
     * Expiration date of daily cake.
     *
     * When it expires, the application switches the current parameter and raffle another cake.
     */
    @Column(nullable = false)
    private Timestamp expiresAt;

}
