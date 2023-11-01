package com.charapadev.cakenviewapi.modules.cakes.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.CakeView;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeViewRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CakeViewService {

    private CakeViewRepository cakeViewRepository;

    public CakeView generate(Cake cake) {
        CakeView newView = CakeView.builder()
            .cake(cake)
            .build();

        return cakeViewRepository.save(newView);
    }

    @Transactional
    public void incrementView(Cake cake) throws NoSuchElementException {
        CakeView viewFound = cakeViewRepository.findByCakeId(cake.getId())
            .orElseGet(() -> generate(cake));

        viewFound.incrementView();
        cakeViewRepository.save(viewFound);
    }

    public List<Cake> listTrendingCakes() {
        return cakeViewRepository.findTrendingCakes();
    }

}
