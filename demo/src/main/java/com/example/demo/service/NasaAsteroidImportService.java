package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.external.nasa.NasaApiClient;
import com.example.demo.external.nasa.NasaAsteroidMapper;
import com.example.demo.repository.AsteroidRepository;
import com.example.demo.external.nasa.dto.NasaNeoBrowseResponse;
import com.example.demo.model.Asteroid;

@Service 
public class NasaAsteroidImportService {

    private final NasaApiClient nasaApiClient;
    private final NasaAsteroidMapper nasaAsteroidMapper;
    private final AsteroidRepository asteroidRepository;

    public NasaAsteroidImportService(
            NasaApiClient nasaApiClient,
            NasaAsteroidMapper nasaAsteroidMapper,
            AsteroidRepository asteroidRepository) {
        this.nasaApiClient = nasaApiClient;
        this.nasaAsteroidMapper = nasaAsteroidMapper;
        this.asteroidRepository = asteroidRepository;
    }
 
    public List<Asteroid> importAsteroids() {
        NasaNeoBrowseResponse response = nasaApiClient.fetchAsteroids();

        List<Asteroid> asteroids = response.getNearEarthObjects()
            .stream()
            .filter(asteroidResponse ->
                    asteroidResponse.getCloseApproachData() != null &&
                    !asteroidResponse.getCloseApproachData().isEmpty())
            .map(nasaAsteroidMapper::toAsteroid)
            .collect(Collectors.toList());

        return asteroidRepository.saveAll(asteroids);
    }

}