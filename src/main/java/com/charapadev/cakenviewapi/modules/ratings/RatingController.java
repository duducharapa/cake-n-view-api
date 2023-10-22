package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import com.charapadev.cakenviewapi.modules.ratings.dtos.RateCakeDTO;
import com.charapadev.cakenviewapi.modules.ratings.dtos.ShowRatingDTO;
import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.utils.PageUtils;

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
    public @ResponseBody Page<ShowRatingDTO> list(
        @RequestParam("cakeId") Long cakeId,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "bestRated", required = false, defaultValue = "true") Boolean bestRated
    ) {
        Cake cake = cakeService.find(cakeId);

        Direction direction = bestRated ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(
            page, PageUtils.PER_PAGE,
            Sort.by(direction, "number").and(Sort.by("createdAt").descending())
        );
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
