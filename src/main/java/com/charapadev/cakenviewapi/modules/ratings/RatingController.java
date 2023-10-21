package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.modules.auth.AuthService;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;
import com.charapadev.cakenviewapi.modules.users.User;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/ratings")
@RestController
@AllArgsConstructor
public class RatingController {

    private RatingService ratingService;
    private CakeService cakeService;
    private AuthService authService;
    private RatingMapper ratingMapper;

    @GetMapping
    public @ResponseBody Page<ShowRatingDTO> list(@RequestParam("cakeId") Long cakeId, Pageable pageable) {
        Cake cake = cakeService.find(cakeId);
        Page<Rating> ratings = ratingService.list(cake, pageable);

        return ratings.map(ratingMapper::toShow);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody @Valid RateCakeDTO rateDTO, Authentication auth) {
        Cake cake = cakeService.find(rateDTO.cakeId());
        User user = authService.getUserFromAuth(auth);

        ratingService.add(cake, user, rateDTO);
    }

}
