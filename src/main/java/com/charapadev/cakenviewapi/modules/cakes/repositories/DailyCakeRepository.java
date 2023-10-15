package com.charapadev.cakenviewapi.modules.cakes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;

public interface DailyCakeRepository extends JpaRepository<DailyCake, Long> {
    
    @Query("SELECT t FROM DailyCake t WHERE t.current = true ORDER BY t.id LIMIT 1")
    Optional<DailyCake> findDailyCake();

    @Query(
        "SELECT c FROM Cake c WHERE c NOT IN " +
        "(SELECT c2 FROM DailyCake t JOIN t.cake c2) " +
        "ORDER BY RANDOM() LIMIT 5" 
    )
    List<Cake> findOptionsForDailyCake();

}
