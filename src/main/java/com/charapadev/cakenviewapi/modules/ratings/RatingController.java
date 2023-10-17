package com.charapadev.cakenviewapi.modules.ratings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/ratings")
@RestController
@AllArgsConstructor
public class RatingController {

    private RatingService ratingService;
    private CakeService cakeService;

    @GetMapping
    public @ResponseBody Page<Rating> list(@RequestParam("cakeId") Long cakeId, Pageable pageable) {
        Cake cake = cakeService.find(cakeId);

        return ratingService.list(cake, pageable);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody @Valid RateCakeDTO rateDTO) {
        Cake cake = cakeService.find(rateDTO.cakeId());

        ratingService.add(cake, rateDTO);
    }

}
