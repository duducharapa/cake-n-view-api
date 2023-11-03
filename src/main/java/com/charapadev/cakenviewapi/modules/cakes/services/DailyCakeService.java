package com.charapadev.cakenviewapi.modules.cakes.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.exceptions.NotFoundException;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeRepository;
import com.charapadev.cakenviewapi.modules.cakes.repositories.DailyCakeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j(topic = "Daily cake service")
public class DailyCakeService {

    private DailyCakeRepository dailyCakeRepository;
    private CakeRepository cakeRepository;

    /**
     * Retrieves the current daily cake.
     *
     * The current cake will be the only one that has the CURRENT parameter set as TRUE.
     *
     * @return The currently daily cake found.
     * @throws NotFoundException Cannot found any daily cake.
     */
    public DailyCake getCurrent() throws NotFoundException {
        return dailyCakeRepository.findDailyCake()
            .orElseThrow(() -> new NotFoundException("Cannot found any daily cake today"));
    }

    /**
     * Switches the previous chosen daily cake and picks one to be the newer chosen.
     *
     * This action, obviously, is perform diarialy.
     */
    @Scheduled(cron = "@daily")
    public void refreshDailyCake() {
        Optional<DailyCake> currentTrending = dailyCakeRepository.findDailyCake();

        currentTrending.ifPresentOrElse(
            // If present
            (dailyCake) -> {
                // Checks if the current cake is already expired
                // If is, invalidates
                Timestamp now = Timestamp.from(Instant.now());
                boolean isExpired = dailyCake.getExpiresAt().after(now);

                if (!isExpired) return;

                dailyCake.setCurrent(false);
                dailyCakeRepository.save(dailyCake);
            },
            // If empty
            () -> raffleNewCake()
        );
    }

    /**
     * Searches the available candidates of daily cake an raffles a random one.
     *
     * To be considerated candidate, the cake cannot was chosen previously.
     * The application will abort this action if has no more cakes as candidates.
     */
    private void raffleNewCake() {
        long cakeCount = cakeRepository.count();
        long previousDailyCakes = dailyCakeRepository.count();

        try {
            // If has no more cakes to raffle
            if (cakeCount <= previousDailyCakes) throw new RuntimeException();

            List<Cake> possibleCakes = dailyCakeRepository.findOptionsForDailyCake();
            int randomNumber = new Random().nextInt(possibleCakes.size());
            Cake choosenCake = possibleCakes.get(randomNumber);

            create(choosenCake);
        } catch (RuntimeException ex) {
            log.error("Cannot raffle a new cake, no remaining candidates.");
        }
    }

    /**
     * Creates a new DailyCake instance.
     *
     * @param cake The cake chosen as daily.
     */
    private void create(Cake cake) {
        int EXPIRE_DAYS = 1;

        Instant tomorrow = Instant.now().plus(EXPIRE_DAYS, ChronoUnit.DAYS);
        Timestamp nextDay = Timestamp.from(tomorrow);

        DailyCake daily = DailyCake.builder()
            .cake(cake)
            .expiresAt(nextDay)
            .build();

        dailyCakeRepository.save(daily);
    }

    /**
     * Deletes an daily cake instance (if exists) based on a related cake.
     *
     * @param cakeId The cake identifier.
     */
    @Transactional
    public void removeFromCake(Long cakeId) {
        Optional<DailyCake> dailyCake = dailyCakeRepository.findByCake(cakeId);

        if (dailyCake.isEmpty()) return;

        dailyCakeRepository.delete(dailyCake.get());
    }

}
