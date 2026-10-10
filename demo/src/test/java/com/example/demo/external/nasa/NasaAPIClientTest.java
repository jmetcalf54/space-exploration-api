package com.example.demo.external.nasa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.external.nasa.dto.NasaNeoBrowseResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class NasaAPIClientTest {
    
    @Autowired
    private NasaApiClient nasaApiClient;


    @Test
    public void testApiKeyIsSet() {
        assertNotNull(nasaApiClient.getApiKey());
        assertFalse(nasaApiClient.getApiKey().isBlank());
    }

    @Test
    public void testFetchAsteroids() {
        NasaNeoBrowseResponse response = nasaApiClient.fetchAsteroids();

        assertNotNull(response);
        assertNotNull(response.getNearEarthObjects());
        assertFalse(response.getNearEarthObjects().isEmpty());
        assertNotNull(response.getNearEarthObjects().get(0).getName());
        assertNotNull(response.getNearEarthObjects().get(0).getAbsoluteMagnitudeH());
    }

}
