package com.charapadev.cakenviewapi.modules.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.exceptions.RestError;
import com.charapadev.cakenviewapi.modules.users.dtos.CreateUserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Usuários", description = "Representa os consumidores da aplicação")
@RequestMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @Operation(summary = "Cadastrar novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(
            responseCode = "422", description = "Já existe um usuário utilizando o email cadastrado",
            content = @Content(schema = @Schema(implementation = RestError.class))
        )
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody void create(@RequestBody @Valid CreateUserDTO createDTO) {
        userService.create(createDTO);
    }

}
