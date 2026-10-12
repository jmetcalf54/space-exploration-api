package com.example.demo.external.nasa;

import org.junit.jupiter.api.Test;

import com.example.demo.external.nasa.dto.NasaAsteroidResponse;
import com.example.demo.external.nasa.dto.NasaCloseApproachResponse;
import com.example.demo.external.nasa.dto.NasaMissDistanceResponse;
import com.example.demo.model.Asteroid;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NasaAsteroidMapperTest {

    @Test
    void testMapsNasaAsteroidToAsteroid() {

        NasaMissDistanceResponse missDistance = new NasaMissDistanceResponse();
        missDistance.setKilometers("12345.67");

        NasaCloseApproachResponse closeApproach = new NasaCloseApproachResponse();
        closeApproach.setMissDistance(missDistance);

        NasaAsteroidResponse nasaAsteroid = new NasaAsteroidResponse();
        nasaAsteroid.setName("Test Asteroid");
        nasaAsteroid.setCloseApproachData(List.of(closeApproach));

        NasaAsteroidMapper mapper = new NasaAsteroidMapper();

        Asteroid asteroid = mapper.toAsteroid(nasaAsteroid);

        assertEquals("Test Asteroid", asteroid.getOfficialName());
        assertEquals(12345.67, asteroid.getDistanceFromEarth());
    }
}