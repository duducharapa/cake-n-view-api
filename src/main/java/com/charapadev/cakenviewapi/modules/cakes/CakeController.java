package com.charapadev.cakenviewapi.modules.cakes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.modules.cakes.dtos.CreateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowDailyCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;
import com.charapadev.cakenviewapi.modules.cakes.services.DailyCakeService;
import com.charapadev.utils.PageUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/cakes")
@RestController
@AllArgsConstructor
public class CakeController {

    private CakeService cakeService;
    private DailyCakeService dailyCakeService;
    private CakeMapper cakeMapper;

    @GetMapping
    public @ResponseBody Page<ShowCakeDTO> list(
        @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "bestRated", required = false, defaultValue = "true") Boolean bestRated
    ) {
        Direction direction = bestRated ? Direction.DESC : Direction.ASC;

        PageRequest pageable = PageRequest.of(page, PageUtils.PER_PAGE, Sort.by(direction, "rating.average"));
        Page<Cake> cakesFound = cakeService.list(pageable, name);

        return cakesFound.map(cakeMapper::toShow);
    }

    @GetMapping("/daily")
    public @ResponseBody ShowDailyCakeDTO getCakeOfDay() {
        DailyCake dailyCake = dailyCakeService.getCurrent();

        return cakeMapper.toShowDaily(dailyCake);
    }

    @GetMapping("/trendings")
    public @ResponseBody List<ShowCakeDTO> listTrending() {
        return cakeService.listTrending().stream()
            .map(cakeMapper::toShow).toList();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody ShowCakeDTO create(@RequestBody @Valid CreateCakeDTO createDTO) {
        Cake newCake = cakeService.create(createDTO);

        return cakeMapper.toShow(newCake);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ShowCakeDTO find(@PathVariable("id") Long cakeId) {
        Cake cakeFound = cakeService.find(cakeId);

        return cakeMapper.toShow(cakeFound);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Long cakeId) {
        cakeService.remove(cakeId);
    }

}
