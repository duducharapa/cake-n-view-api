package com.charapadev.cakenviewapi.modules.cakes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;

public interface DailyCakeRepository extends JpaRepository<DailyCake, Long> {

    /**
     * Searches the currently daily cake.
     *
     * @return The daily cake found.
     */
    @Query("SELECT t FROM DailyCake t WHERE t.current = true ORDER BY t.id LIMIT 1")
    Optional<DailyCake> findDailyCake();

    /**
     * Select some cakes that would be candidates for next daily cake.
     *
     * To be an candidate, the cake just need to not was already chosen previously.
     *
     * @return A list containing he options for daily cakes as.
     */
    @Query(
        "SELECT c FROM Cake c WHERE c NOT IN " +
        "(SELECT c2 FROM DailyCake t JOIN t.cake c2) " +
        "ORDER BY RANDOM() LIMIT 5"
    )
    List<Cake> findOptionsForDailyCake();

    /**
     * Searches an DailyCake registry by a given cake.
     *
     * @param cakeId The cake identifier.
     * @return The daily cake found.
     */
    @Query("SELECT d FROM DailyCake d JOIN d.cake c WHERE c.id = :cakeId")
    Optional<DailyCake> findByCake(Long cakeId);

}
