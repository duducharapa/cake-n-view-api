package com.charapadev.cakenviewapi.modules.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.modules.users.dtos.CreateUserDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User create(CreateUserDTO createDTO) {
        String encodedPass = passwordEncoder.encode(createDTO.password());

        User newUser = User.builder()
            .email(createDTO.email())
            .password(encodedPass)
            .build();

        return userRepository.save(newUser);
    }

}
