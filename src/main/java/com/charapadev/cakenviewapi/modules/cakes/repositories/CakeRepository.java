package com.charapadev.cakenviewapi.modules.cakes.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;

public interface CakeRepository extends JpaRepository<Cake, Long> {

    @Query("SELECT c FROM Cake c WHERE lower(c.name) LIKE lower(concat('%', :name, '%'))")
    Page<Cake> findAllByName(String name, Pageable pageable);

}
