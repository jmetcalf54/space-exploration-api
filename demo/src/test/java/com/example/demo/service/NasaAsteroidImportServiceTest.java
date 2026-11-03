package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.external.nasa.NasaApiClient;
import com.example.demo.external.nasa.NasaAsteroidMapper;
import com.example.demo.external.nasa.dto.NasaAsteroidResponse;
import com.example.demo.external.nasa.dto.NasaCloseApproachResponse;
import com.example.demo.external.nasa.dto.NasaNeoBrowseResponse;
import com.example.demo.model.Asteroid;
import com.example.demo.repository.AsteroidRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NasaAsteroidImportServiceTest {

    @Mock
    private NasaApiClient nasaApiClient;

    @Mock
    private NasaAsteroidMapper nasaAsteroidMapper;

    @Mock
    private AsteroidRepository asteroidRepository;

    private NasaAsteroidImportService importService;

    @BeforeEach
    void setUp() {
        importService = new NasaAsteroidImportService(
                nasaApiClient,
                nasaAsteroidMapper,
                asteroidRepository
        );
    }

    @Test
    void testImportAsteroids() {

        NasaCloseApproachResponse closeApproach =
                new NasaCloseApproachResponse();

        NasaAsteroidResponse nasaAsteroid =
                new NasaAsteroidResponse();

        nasaAsteroid.setId("123456");
        nasaAsteroid.setName("Test Asteroid");
        nasaAsteroid.setCloseApproachData(List.of(closeApproach));

        NasaNeoBrowseResponse nasaResponse =
                new NasaNeoBrowseResponse();

        nasaResponse.setNearEarthObjects(
                List.of(nasaAsteroid)
        );

        Asteroid mappedAsteroid =
                new Asteroid(
                        "Test Asteroid",
                        12345.67,
                        3,
                        true,
                        "123456"
                );

        when(nasaApiClient.fetchAsteroids())
                .thenReturn(nasaResponse);

        when(nasaAsteroidMapper.toAsteroid(nasaAsteroid))
                .thenReturn(mappedAsteroid);

        when(asteroidRepository.saveAll(
                List.of(mappedAsteroid)))
                .thenReturn(List.of(mappedAsteroid));

        List<Asteroid> result =
                importService.importAsteroids();

        assertEquals(1, result.size());
        assertEquals(
                "Test Asteroid",
                result.get(0).getOfficialName()
        );
        assertEquals(
                "123456",
                result.get(0).getNasaId()
        );

        verify(nasaAsteroidMapper)
                .toAsteroid(nasaAsteroid);

        verify(asteroidRepository)
                .saveAll(List.of(mappedAsteroid));
    }
}