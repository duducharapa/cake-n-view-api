package com.charapadev.cakenviewapi.modules.users;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.users.dtos.ShowUserDTO;

/**
 * Mapper used to convert the User instance to prevent expose some sensitive data like passwords.
 */

@Component
public class UserMapper {

    /**
     * Converts a given User instance into a DTO containing the public data.
     *
     * @param user The user instance.
     * @return The user public data.
     */
    public ShowUserDTO toShow(User user) {
        return new ShowUserDTO(
            user.getId(),
            user.getName()
        );
    }

}
