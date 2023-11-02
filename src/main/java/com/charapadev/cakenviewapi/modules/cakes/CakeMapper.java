package com.charapadev.cakenviewapi.modules.cakes;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowDailyCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;

/**
 * Mapper used to convert the data of any type of cake instance (DailyCake, Cake, etc) to better relevant schemas, where
 * the application hiddens unecessary data for view logic.
 */

@Component
public class CakeMapper {

    /**
     * Convert a pure Cake instance to a public data.
     *
     * @param cake The cake to convert.
     * @return The public cake data.
     */
    public ShowCakeDTO toShow(Cake cake) {
        return new ShowCakeDTO(
            cake.getId(),
            cake.getName(),
            cake.getDescription(),
            cake.getImageUrl(),
            cake.getRating()
        );
    }

    /**
     * Converts a DailyCake instance to a better clean schema, removing internal logic parameters.
     *
     * @param daily The DailyCake to convert.
     * @return A cleaner daily cake data.
     */
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
