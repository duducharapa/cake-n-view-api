package com.charapadev.cakenviewapi.modules.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody User create(@RequestBody @Valid CreateUserDTO createDTO) {
        return userService.create(createDTO);
    }

}
