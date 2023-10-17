package com.charapadev.cakenviewapi.modules.users;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

@Component
public class UserMapper {

    public ShowUserDTO toShow(User user) {
        return new ShowUserDTO(
            user.getId(),
            user.getEmail()
        );
    }

}
