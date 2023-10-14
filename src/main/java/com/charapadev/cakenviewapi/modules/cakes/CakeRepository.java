package com.charapadev.cakenviewapi.modules.cakes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CakeRepository extends JpaRepository<Cake, Long> {
    
    @Query("SELECT c FROM Cake c ORDER BY c.id LIMIT 3")
    List<Cake> findTrending();

}
