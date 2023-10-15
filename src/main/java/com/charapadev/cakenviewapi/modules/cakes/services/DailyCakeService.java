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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DailyCakeService {
    
    private DailyCakeRepository dailyCakeRepository;
    private CakeRepository cakeRepository;

    public DailyCake getCurrent() {
        return dailyCakeRepository.findDailyCake()
            .orElseThrow(() -> new NotFoundException("Cannot found any daily cake today"));
    }

    @Scheduled(cron = "@daily")
    public void refreshDailyCake() {
        Optional<DailyCake> currentTrending = dailyCakeRepository.findDailyCake();

        currentTrending.ifPresent((dailyCake) -> {
            dailyCake.setCurrent(false);
            dailyCakeRepository.save(dailyCake);
        });

        raffleNewCake();
    }

    private void raffleNewCake() {
        long cakeCount = cakeRepository.count();
        long trendingCount = dailyCakeRepository.count();

        // If has no more cakes to raffle
        if (cakeCount <= trendingCount) {
            //throw new RuntimeException();
            return;
        }

        List<Cake> possibleCakes = dailyCakeRepository.findOptionsForDailyCake();
        int randomNumber = new Random().nextInt(possibleCakes.size());
        Cake choosenCake = possibleCakes.get(randomNumber);

        create(choosenCake);
    }

    private void create(Cake cake) {
        Instant tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);
        Timestamp nextDay = Timestamp.from(tomorrow);

        DailyCake daily = DailyCake.builder()
            .cake(cake)
            .expiresAt(nextDay)
            .build();

        dailyCakeRepository.save(daily);
    }



}
