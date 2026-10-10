package com.example.demo.external.nasa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.demo.external.nasa.dto.NasaNeoBrowseResponse;

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

    public NasaNeoBrowseResponse fetchAsteroids() {
        return restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/neo/rest/v1/neo/browse")
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .body(NasaNeoBrowseResponse.class);
    }

    public String getApiKey() {
        return apiKey;
    }
}
