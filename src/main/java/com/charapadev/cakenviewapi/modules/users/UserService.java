package com.charapadev.cakenviewapi.modules.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.UnprocessableEntityException;
import com.charapadev.cakenviewapi.modules.users.dtos.CreateUserDTO;

import lombok.AllArgsConstructor;

/**
 * Service used to perform some basic actions related to User instances like registration.
 */

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user on application.
     *
     * @param createDTO The user data to register.
     * @throws UnprocessableEntityException The email is already owned by an user.
     */
    public void create(CreateUserDTO createDTO) throws UnprocessableEntityException {
        boolean alreadyExists = userRepository.existsUserByEmail(createDTO.email());
        if (alreadyExists) throw new UnprocessableEntityException("The provided email is already registered by other user");

        String encodedPass = passwordEncoder.encode(createDTO.password());

        User newUser = User.builder()
            .email(createDTO.email())
            .name(createDTO.name())
            .password(encodedPass)
            .build();

        userRepository.save(newUser);
    }

}
