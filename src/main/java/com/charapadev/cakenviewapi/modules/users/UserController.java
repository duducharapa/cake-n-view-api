package com.charapadev.cakenviewapi.modules.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.exceptions.RestError;
import com.charapadev.cakenviewapi.modules.auth.AuthService;
import com.charapadev.cakenviewapi.modules.ratings.RatingMapper;
import com.charapadev.cakenviewapi.modules.ratings.RatingService;
import com.charapadev.cakenviewapi.modules.ratings.dtos.ShowRatingDTO;
import com.charapadev.cakenviewapi.modules.users.dtos.CreateUserDTO;
import com.charapadev.cakenviewapi.utils.PageUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Usuários", description = "Representa os consumidores da aplicação")
@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthService authService;
    private RatingService ratingService;
    private RatingMapper ratingMapper;

    @Operation(summary = "Cadastrar novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(
            responseCode = "422", description = "Já existe um usuário utilizando o email cadastrado",
            content = @Content(schema = @Schema(implementation = RestError.class))
        )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody void create(@RequestBody @Valid CreateUserDTO createDTO) {
        userService.create(createDTO);
    }

    @Operation(summary = "Listar avaliações do usuário")
    @GetMapping("/ratings")
    public @ResponseBody Page<ShowRatingDTO> myRatings(
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        Authentication auth
    ) {
        User userFound = authService.getUserFromAuth(auth);

        return ratingService.listByUser(userFound, PageRequest.of(page, PageUtils.PER_PAGE))
            .map(ratingMapper::toShowWithCake);
    }

}
