package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.users.UserMapper;
import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RatingMapper {

    private UserMapper userMapper;

    public ShowRatingDTO toShow(Rating rating) {
        ShowUserDTO user = userMapper.toShow(rating.getUser());

        return new ShowRatingDTO(
            rating.getId(),
            rating.getNumber(),
            rating.getComment(),
            user
        );
    }

}
