package com.charapadev.cakenviewapi.modules.cakes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CakeService {
    
    private CakeRepository cakeRepository;

    public Page<Cake> list(Pageable pageable) {
        return cakeRepository.findAll(pageable);
    }

    public List<Cake> listTrending() {
        return cakeRepository.findTrending();
    }

    public Cake create(CreateCakeDTO createDTO) {
        Cake newCake = Cake.builder()
            .name(createDTO.name())
            .description(createDTO.description())
            .imageUrl(createDTO.imageUrl())
            .build();

        return cakeRepository.save(newCake);
    }

    public Cake find(Long cakeId, String errorMessage) throws NotFoundException {
        Cake cakeFound = cakeRepository.findById(cakeId)
            .orElseThrow(() -> new NotFoundException(errorMessage));
        
        return cakeFound;
    }

    public Cake find(Long cakeId) throws NotFoundException {
        String message = String.format("Cannot found an cake using the given ID: %s", cakeId);
        return find(cakeId, message);
    }

    public void remove(Long cakeId) {
        find(cakeId);

        cakeRepository.deleteById(cakeId);
    }

}
