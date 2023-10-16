package com.charapadev.cakenviewapi.modules.ratings;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r JOIN r.cake c WHERE c.id = :cakeId")
    Page<Rating> findAllByCake(Long cakeId, Pageable pageable);

    @Query("SELECT avg(r.number) FROM Rating r JOIN r.cake c WHERE c.id = :cakeId")
    BigDecimal getAverageRatingFromCake(Long cakeId);
    
}
