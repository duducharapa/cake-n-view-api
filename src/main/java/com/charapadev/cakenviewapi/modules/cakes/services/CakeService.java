package com.charapadev.cakenviewapi.modules.cakes.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.NotFoundException;
import com.charapadev.cakenviewapi.modules.cakes.dtos.CreateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.UpdateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeRepository;

import lombok.AllArgsConstructor;

/**
 * Service used to deal with common manipulation of cakes resources like create one, delete or list them.
 */

@Service
@AllArgsConstructor
public class CakeService {

    private CakeRepository cakeRepository;
    private CakeViewService cakeViewService;

    /**
     * Lists all cakes, filtered by name and pagination options.
     *
     * @param pageable The pagination object.
     * @param name The desired name.
     * @return The page containing the cakes found.
     */
    public Page<Cake> list(Pageable pageable, String name) {
        return cakeRepository.findAllByName(name, pageable);
    }

    /**
     *
     *
     * @param createDTO
     * @return
     */
    public Cake create(CreateCakeDTO createDTO) {
        Cake newCake = Cake.builder()
            .name(createDTO.name())
            .description(createDTO.description())
            .imageUrl(createDTO.imageUrl())
            .build();

        newCake = cakeRepository.save(newCake);
        cakeViewService.generate(newCake);

        return newCake;
    }

    /**
     * Searches a cake using the given identifier.
     *
     * @param cakeId The cake identifier.
     * @param errorMessage An custom error message.
     * @return The cake found.
     * @throws NotFoundException If the cake cannot be found.
     */
    public Cake find(Long cakeId, String errorMessage) throws NotFoundException {
        Cake cakeFound = cakeRepository.findById(cakeId)
            .orElseThrow(() -> new NotFoundException(errorMessage));

        return cakeFound;
    }

    /**
     * Shortcut for find method to search some cake.
     *
     * This polymorphism is used to evict passes repetitive error messages on find method.
     *
     * @param cakeId The cake identifier.
     * @return The cake found.
     * @throws NotFoundException If the cake cannot be found.
     */
    public Cake find(Long cakeId) throws NotFoundException {
        String message = String.format("Cannot found an cake using the given ID: %s", cakeId);
        return find(cakeId, message);
    }

    /**
     * Updates the given data about an specific cake.
     *
     * @param cake The cake to update.
     * @param updateDTO The data to be refreshed.
     */
    public void update(Cake cake, UpdateCakeDTO updateDTO) {
        Optional.ofNullable(updateDTO.rating()).ifPresent(rating -> {
            cake.getRating().setAverage(rating);
            cake.getRating().incrementRatings();
        });

        cakeRepository.save(cake);
    }

    /**
     * Removes an cake
     *
     * If the cake is currently trending or was chosen as daily cake, the application will fail to delete it.
     * It'll be fixed on incoming updates.
     *
     * @param cakeId The cake identifier.
     */
    // FIXME: Fix removable daily cakes
    public void remove(Long cakeId) {
        find(cakeId);

        cakeRepository.deleteById(cakeId);
    }

}
