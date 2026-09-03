package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.model.Asteroid;
import com.example.demo.model.NearEarthObjects;
import com.example.demo.service.SpaceObjectService;


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
    }

    @Test
    void testThreatLevelFilteringWithInvalidInput() {
        // Implement test logic for invalid input handling
    }

    @Test
    void testClosestSpaceObject() {
        // Implement test logic for closest space object retrieval
    }

}
