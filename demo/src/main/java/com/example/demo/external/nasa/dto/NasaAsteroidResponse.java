package com.example.demo.external.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaAsteroidResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("absolute_magnitude_h")
    private Double absoluteMagnitudeH;

    public String getName() {
        return name;
    }

    public Double getAbsoluteMagnitudeH() {
        return absoluteMagnitudeH;
    }
    
}
