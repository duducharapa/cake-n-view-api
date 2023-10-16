package com.charapadev.cakenviewapi.modules.ratings;

import java.math.RoundingMode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.modules.cakes.dtos.UpdateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingService {
    
    private RatingRepository ratingRepository;
    private CakeService cakeService;

    @Transactional
    public Page<Rating> list(Cake cake, Pageable pageable) {
        return ratingRepository.findAllByCake(cake.getId(), pageable);
    }

    @Transactional
    public void add(Cake cake, RateCakeDTO rateDTO) {
        Rating newRating = Rating.builder()
            .cake(cake)
            .comment(rateDTO.comment())
            .number(rateDTO.number())
            .build();

        ratingRepository.save(newRating);
        updateCakeRating(cake);
    }

    private void updateCakeRating(Cake cake) {
        Double newAverage = ratingRepository.getAverageRatingFromCake(cake.getId())
            .setScale(2, RoundingMode.HALF_UP).doubleValue();

        UpdateCakeDTO updatedRating = UpdateCakeDTO.builder()
            .rating(newAverage)
            .build();
        cakeService.update(cake, updatedRating);
    }

}
