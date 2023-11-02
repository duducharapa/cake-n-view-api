package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.stereotype.Component;

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

    /**
     * Converts a Ratings instance into a cleaner DTO.
     *
     * @param rating The rating instance.
     * @return The cleaner rating data.
     */
    public ShowRatingDTO toShow(Rating rating) {
        ShowUserDTO user = userMapper.toShow(rating.getUser());

        return new ShowRatingDTO(
            rating.getId(),
            rating.getNumber(),
            rating.getComment(),
            rating.getCreatedAt(),
            user
        );
    }

}
