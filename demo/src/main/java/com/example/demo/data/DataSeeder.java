package com.example.demo.data;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.model.Comet;
import com.example.demo.repository.AsteroidRepository;
import com.example.demo.repository.CometRepository;

@Component 
public class DataSeeder implements CommandLineRunner {
    private final CometRepository cometRepository;
    private final AsteroidRepository asteroidRepository;

    public DataSeeder(AsteroidRepository asteroidRepository, CometRepository cometRepository) {
        this.asteroidRepository = asteroidRepository;
        this.cometRepository = cometRepository;
    }

    @Override
    public void run(String... args) {
        if (asteroidRepository.count() == 0) {
            asteroidRepository.saveAll(List.of(
                new com.example.demo.model.Asteroid("X-1002342", 100.0, 1),
                new com.example.demo.model.Asteroid("Devastator", 10000.000, 2)
            ));
        }
        if (cometRepository.count() == 0) {
            cometRepository.saveAll(List.of(
                new Comet("Halley's Comet", 100.0, "500km"),
                new Comet("Red Rocket", 10000.000, "1000km")
            ));
        }
    }
}
