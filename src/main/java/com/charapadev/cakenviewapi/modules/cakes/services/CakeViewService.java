package com.charapadev.cakenviewapi.modules.cakes.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.CakeView;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeViewRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * Service used to perform actions related to visualization of cakes itself.
 */

@Service
@AllArgsConstructor
public class CakeViewService {

    private CakeViewRepository cakeViewRepository;

    /**
     * Generates a CakeView instance related to some cake passed.
     *
     * @param cake The cake instance.
     * @return The CakeView generated.
     */
    public CakeView generate(Cake cake) {
        CakeView newView = CakeView.builder()
            .cake(cake)
            .build();

        return cakeViewRepository.save(newView);
    }

    /**
     * Searches a CakeView instance using a given cake.
     *
     * @param cakeId The cake identifier.
     * @return The views instance found.
     */
    public Optional<CakeView> findByCake(Long cakeId) {
        return cakeViewRepository.findByCakeId(cakeId);
    }

    /**
     * Performs an increment of view on desired cake.
     *
     * @param cake The cake visited.
     * @throws NoSuchElementException Cannot find an CakeView related to given cake.
     */
    @Transactional
    public void incrementView(Cake cake) throws NoSuchElementException {
        CakeView viewFound = findByCake(cake.getId())
            .orElseGet(() -> generate(cake));

        viewFound.incrementView();
        cakeViewRepository.save(viewFound);
    }

    /**
     * Lists the currently trending cakes on application.
     *
     * Trending cakes are the most visited cakes in total time of application's existance.
     *
     * @return The trending cakes.
     */
    public List<Cake> listTrendingCakes() {
        return cakeViewRepository.findTrendingCakes();
    }

}
