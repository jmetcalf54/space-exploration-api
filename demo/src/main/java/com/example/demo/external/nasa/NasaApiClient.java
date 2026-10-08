package com.example.demo.external.nasa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NasaApiClient {

    @Value("${nasa.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public NasaApiClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.nasa.gov")
                .build();
    }

    public String fetchAsteroids() {
        return restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/neo/rest/v1/neo/browse")
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .body(String.class);
    }

    public String getApiKey() {
        return apiKey;
    }
}
