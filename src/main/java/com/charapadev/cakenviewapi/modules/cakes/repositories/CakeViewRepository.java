package com.charapadev.cakenviewapi.modules.cakes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.CakeView;

public interface CakeViewRepository extends JpaRepository<CakeView, Long> {

    /**
     * Searches the CakeView related to some cake using the given cake identifier.
     *
     * @param cakeId The cake identifier.
     * @return The cake found.
     */
    @Query("SELECT v FROM CakeView v JOIN v.cake c WHERE c.id = :cakeId")
    Optional<CakeView> findByCakeId(Long cakeId);

    /**
     * Searches the trending cakes based on visualizations counter of each one.
     *
     * The application only get the 3 most visited cakes to be considered as trending.
     *
     * @return The trending cakes found.
     */
    @Query("SELECT c FROM CakeView v JOIN v.cake c ORDER BY v.views DESC, c.id ASC LIMIT 3")
    List<Cake> findTrendingCakes();

}
