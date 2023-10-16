package com.charapadev.cakenviewapi.modules.cakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;

public interface CakeRepository extends JpaRepository<Cake, Long> {
    
    @Query("SELECT c FROM Cake c ORDER BY c.id LIMIT 3")
    List<Cake> findTrending();

}