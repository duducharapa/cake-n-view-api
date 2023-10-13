package com.charapadev.cakenviewapi.modules.cakes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<Cake, Long> {
    
}
