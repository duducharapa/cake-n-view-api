package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.cakes.CakeMapper;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowCakeDTO;
import com.charapadev.cakenviewapi.modules.ratings.dtos.ShowRatingDTO;
import com.charapadev.cakenviewapi.modules.users.UserMapper;
import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

import lombok.AllArgsConstructor;

/**
 * Mapper used to convert the ratings instance into more cleaner instances.
 */

@Component
@AllArgsConstructor
public class RatingMapper {

    private UserMapper userMapper;
    private CakeMapper cakeMapper;

    /**
     * Converts a Rating instance into a cleaner DTO.
     *
     * This method maps this instance to show the users that made the rating. It is used when
     * the flux starts from some cake listing/searching.
     *
     * @param rating The rating instance.
     * @return The cleaner rating data.
     */
    public ShowRatingDTO toShowWithUser(Rating rating) {
        ShowUserDTO user = userMapper.toShow(rating.getUser());

        return ShowRatingDTO.builder()
            .id(rating.getId())
            .number(rating.getNumber())
            .comment(rating.getComment())
            .createdAt(rating.getCreatedAt())
            .user(user)
            .build();
    }

    /**
     * Converts a Rating instance into a cleaner DTO.
     *
     * This method maps this instance to show the cake that was rated.
     * It is used when the flux starts from some user listing, like "see my ratings".
     *
     * @param rating The rating instance.
     * @return The cleaner rating data.
     */
    public ShowRatingDTO toShowWithCake(Rating rating) {
        ShowCakeDTO cake = cakeMapper.toShow(rating.getCake());

        return ShowRatingDTO.builder()
            .id(rating.getId())
            .number(rating.getNumber())
            .comment(rating.getComment())
            .createdAt(rating.getCreatedAt())
            .cake(cake)
            .build();
    }

}
