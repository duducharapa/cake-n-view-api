package com.charapadev.cakenviewapi.modules.ratings;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Find a specific rating made by an user for a specific cake.
     *
     * @param cakeId The cake identifier.
     * @param userId The user identifier.
     * @return The rating found.
     */
    @Query("SELECT r FROM Rating r JOIN r.cake c JOIN r.user u WHERE c.id = :cakeId AND u.id = :userId")
    Optional<Rating> findByCakeAndUser(Long cakeId, Long userId);

    /**
     * Searches some ratings related to a specific cake.
     *
     * @param cakeId The cake identifier.
     * @param pageable The pagination object.
     * @return A page containing the rates
     */
    @Query("SELECT r FROM Rating r JOIN r.cake c WHERE c.id = :cakeId")
    Page<Rating> findAllByCake(Long cakeId, Pageable pageable);

    /**
     * Calculates the ratings average of a specific cake.
     *
     * @param cakeId The cake identifier.
     * @return The new ratings average.
     */
    @Query("SELECT avg(r.number) FROM Rating r JOIN r.cake c WHERE c.id = :cakeId")
    BigDecimal getAverageRatingFromCake(Long cakeId);

}
