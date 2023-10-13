package com.charapadev.cakenviewapi.modules.cakes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/cakes")
@RestController
@AllArgsConstructor
public class CakeController {
    
    private CakeService cakeService;

    @GetMapping
    public @ResponseBody Page<Cake> list(Pageable pageable) {
        return cakeService.list(pageable);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Cake create(@RequestBody CreateCakeDTO createDTO) {
        return cakeService.create(createDTO);
    }

    @GetMapping("/{id}")
    public @ResponseBody Cake find(@PathVariable("id") Long cakeId) {
        return cakeService.find(cakeId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Long cakeId) {
        cakeService.remove(cakeId);
    }

}