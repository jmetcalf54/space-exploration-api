package com.example.demo.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.Asteroid;
import com.example.demo.model.NearEarthObjects;


public class SpaceObjectServiceTest {
    private SpaceObjectService spaceObjectService;

    @BeforeEach
    void setup() {
        spaceObjectService = new SpaceObjectService();
    }

    @Test 
    void testThreatLevelFiltering() {
        // Implement test logic for threat level filtering
        List<Asteroid> result =
            spaceObjectService.returnAsteroidsByThreatLevel(2);

        assertEquals(1, result.size());
        assertEquals("Devastator", result.get(0).getOfficialName());
    }

    @Test
    void testMinimumThreatLevelFiltering() {
        // Implement test logic for minimum threat level filtering
        List<Asteroid> result = 
            spaceObjectService.returnMinThreatLevelAsteroids(1);
        
        assertEquals(2, result.size());
        assertEquals("X-1002342", result.get(0).getOfficialName());
    }

    @Test
    void testThreatLevelFilteringWithInvalidInput() {
        // Implement test logic for invalid input handling
        assertThrows(IllegalArgumentException.class, () -> {
            spaceObjectService.returnAsteroidsByThreatLevel(-1);
        });
    }

    @Test
    void testClosestSpaceObject() {
        // Implement test logic for closest space object retrieval
        NearEarthObjects result = 
            spaceObjectService.returnClosestSpaceObject();
        assertEquals(100.0, result.getDistanceFromEarth());
    }
}