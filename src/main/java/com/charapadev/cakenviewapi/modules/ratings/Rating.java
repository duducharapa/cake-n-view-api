package com.charapadev.cakenviewapi.modules.ratings;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the values given by users for cakes.
 *
 * It is like the directly interaction from users with the cakes, sharing some aspects about your expirence with it.
 */

@Entity
@Table(name = "ratings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Rating implements Serializable {

    /**
     * Rating internal identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The cake is being rated.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Cake cake;

    /**
     * The user that rating.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // TODO: Considers the values of ratings for daily cake candidates.
    /**
     * The value, from 0 to 5, given from user to cake.
     *
     * It can be used as a satisfaction degree to consider on filtering.
     */
    @Column(nullable = false)
    private Double number;

    // TODO: Filter the comments
    /**
     * An brief description about the reason for this value.
     *
     * It is optional, but some users want to expose some points of view.
     */
    @Column(columnDefinition = "TEXT")
    private String comment;

    /**
     * Date when the user posted the rating for public view.
     */
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return Objects.equals(getId(), rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
