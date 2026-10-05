package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.demo.model.Asteroid;

@DataJpaTest
public class AsteroidRepositoryTest {
    @Autowired
    private AsteroidRepository asteroidRepository;

    @BeforeEach
    void setUp() {
        asteroidRepository.saveAll(List.of(
            new Asteroid("X-1002342", 100.0, 1),
            new Asteroid("Devastator", 10000.0, 2)
        ));
    }

    @Test
    void testFindByThreatLevel() {
        List<Asteroid> asteroids = asteroidRepository.findByThreatLevel(2);
        assertEquals(1, asteroids.size());
        assertEquals("Devastator", asteroids.get(0).getOfficialName());
    }

    @Test
    void testFindByThreatLevelGreaterThanEqual() {
        List<Asteroid> asteroids = asteroidRepository.findByThreatLevelGreaterThanEqual(1);
        assertEquals(2, asteroids.size());
    }

}
