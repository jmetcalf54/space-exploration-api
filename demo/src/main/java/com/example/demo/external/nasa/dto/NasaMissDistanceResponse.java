package com.example.demo.external.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaMissDistanceResponse {

    @JsonProperty("kilometers")
    private String kilometers;

    public String getKilometers() {
        return kilometers;
    }

}
