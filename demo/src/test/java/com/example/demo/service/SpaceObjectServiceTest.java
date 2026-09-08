package com.example.demo.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.example.demo.model.Asteroid;
import com.example.demo.model.NearEarthObject;
import com.example.demo.repository.AsteroidRepository;

@ExtendWith(MockitoExtension.class)
public class SpaceObjectServiceTest {
    private SpaceObjectService spaceObjectService;

    @Mock
    private AsteroidRepository asteroidRepository;

    @BeforeEach
    void setUp() {
        spaceObjectService = new SpaceObjectService(asteroidRepository);
    }

    @Test 
    void testThreatLevelFiltering() {
        when(asteroidRepository.findAll()).thenReturn(List.of(
            new Asteroid("X-1002342", 100.0, 1),
            new Asteroid("Devastator", 10000.000, 2)
        ));
        List<Asteroid> result =
            spaceObjectService.returnAsteroidsByThreatLevel(2);

        assertEquals(1, result.size());
        assertEquals("Devastator", result.get(0).getOfficialName());
    }

    @Test
    void testMinimumThreatLevelFiltering() {
        when(asteroidRepository.findAll()).thenReturn(List.of(
            new Asteroid("X-1002342", 100.0, 1),
            new Asteroid("Devastator", 10000.000, 2)
        ));
        List<Asteroid> result = 
            spaceObjectService.returnMinThreatLevelAsteroids(1);
        
        assertEquals(2, result.size());
        assertEquals("X-1002342", result.get(0).getOfficialName());
    }

    @Test
    void testThreatLevelFilteringWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            spaceObjectService.returnAsteroidsByThreatLevel(-1);
        });
    }

    @Test
    void testClosestSpaceObject() {
        when(asteroidRepository.findAll()).thenReturn(List.of(
            new Asteroid("X-1002342", 100.0, 1),
            new Asteroid("Devastator", 10000.000, 2)
        ));
        NearEarthObject result = 
            spaceObjectService.returnClosestSpaceObject();
        assertEquals(100.0, result.getDistanceFromEarth());
    }
}