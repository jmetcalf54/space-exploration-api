package com.example.demo.data;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.repository.AsteroidRepository;

@Component 
public class DataSeeder implements CommandLineRunner {
    private final AsteroidRepository asteroidRepository;

    public DataSeeder(AsteroidRepository asteroidRepository) {
        this.asteroidRepository = asteroidRepository;
    }

    @Override
    public void run(String... args) {
        if (asteroidRepository.count() == 0) {
            asteroidRepository.saveAll(List.of(
                new com.example.demo.model.Asteroid("X-1002342", 100.0, 1),
                new com.example.demo.model.Asteroid("Devastator", 10000.000, 2)
            ));
        }
    }
}
