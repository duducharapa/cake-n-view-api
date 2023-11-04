package com.charapadev.cakenviewapi.modules.ratings;

import java.math.RoundingMode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.UnprocessableEntityException;
import com.charapadev.cakenviewapi.modules.cakes.dtos.UpdateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;
import com.charapadev.cakenviewapi.modules.ratings.dtos.RateCakeDTO;
import com.charapadev.cakenviewapi.modules.users.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * Service used to manipulate the common actions about Rating instances.
 */

@Service
@AllArgsConstructor
public class RatingService {

    private RatingRepository ratingRepository;
    private CakeService cakeService;

    /**
     * Searches the ratings related to a given cake.
     *
     * @param cake The rated cake.
     * @param pageable The pagination object.
     * @return A page containing the ratings related.
     */
    @Transactional
    public Page<Rating> list(Cake cake, Pageable pageable) {
        return ratingRepository.findAllByCake(cake.getId(), pageable);
    }

    /**
     * Posts a new rating sent by an user for a cake.
     *
     * @param cake The cake to rate.
     * @param user The evaluator user.
     * @param rateDTO The data of rating itself.
     * @throws UnprocessableEntityException The user already rated this cake.
     */
    @Transactional
    public void add(Cake cake, User user, RateCakeDTO rateDTO) throws UnprocessableEntityException {
        boolean alreadyRatedByUser = ratingRepository.findByCakeAndUser(cake.getId(), user.getId())
            .isPresent();
        if (alreadyRatedByUser) throw new UnprocessableEntityException("This user already rated this cake");

        Rating newRating = Rating.builder()
            .cake(cake)
            .user(user)
            .comment(rateDTO.comment())
            .number(rateDTO.number())
            .build();

        ratingRepository.save(newRating);
        updateCakeRating(cake);
    }

    /**
     * Updates the general rating of a given cake.
     *
     * It recalculates the new rating average and increments the counter.
     *
     * @param cake A cake instance.
     */
    private void updateCakeRating(Cake cake) {
        Double newAverage = ratingRepository.getAverageRatingFromCake(cake.getId())
            .setScale(2, RoundingMode.HALF_UP).doubleValue();

        UpdateCakeDTO updatedRating = UpdateCakeDTO.builder()
            .rating(newAverage)
            .build();
        cakeService.update(cake, updatedRating);
    }

    /**
     * Searches the ratings made by an user.
     *
     * @param user The ratings creator.
     * @param pageable The pagination object.
     * @return A page containing some user ratings.
     */
    public Page<Rating> listByUser(User user, Pageable pageable) {
        return ratingRepository.findAllByUser(user.getId(), pageable);
    }

}
