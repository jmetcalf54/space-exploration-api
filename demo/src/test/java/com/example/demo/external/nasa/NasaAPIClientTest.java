package com.example.demo.external.nasa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
