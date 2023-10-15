package com.charapadev.cakenviewapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.charapadev.cakenviewapi.modules.cakes.services.DailyCakeService;

@SpringBootApplication
@EnableScheduling
public class CakenviewApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakenviewApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(DailyCakeService dailyCakeService) {
		return args -> dailyCakeService.refreshDailyCake();
	} 

}
