package com.charapadev.cakenviewapi.modules.cakes;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowDailyCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;

@Component
public class CakeMapper {

    public ShowCakeDTO toShow(Cake cake) {
        return new ShowCakeDTO(
            cake.getId(),
            cake.getName(),
            cake.getDescription(),
            cake.getImageUrl(),
            cake.getRating()
        );
    }

    public ShowDailyCakeDTO toShowDaily(DailyCake daily) {
        ShowCakeDTO cake = toShow(daily.getCake());

        // Conversion on Timestamp from database do LocalDateTime
        String expiresAt = LocalDateTime.ofInstant(
            daily.getExpiresAt().toInstant(),
            TimeZone.getDefault().toZoneId()
        ).toString();

        return new ShowDailyCakeDTO(
            cake,
            expiresAt
        );
    }

}
