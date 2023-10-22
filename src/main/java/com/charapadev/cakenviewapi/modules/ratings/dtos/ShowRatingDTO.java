package com.charapadev.cakenviewapi.modules.ratings.dtos;

import java.sql.Timestamp;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

public record ShowRatingDTO(
    Long id,
    Double number,
    String comment,
    Timestamp createdAt,
    ShowUserDTO user
) {

}
