package com.charapadev.cakenviewapi.modules.ratings;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

public record ShowRatingDTO(
    Long id,
    Double number,
    String comment,
    ShowUserDTO user
) {

}
