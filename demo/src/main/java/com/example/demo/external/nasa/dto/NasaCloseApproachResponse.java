package com.example.demo.external.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaCloseApproachResponse {

    @JsonProperty ("miss_distance")
    private NasaMissDistanceResponse missDistance;

    public NasaMissDistanceResponse getMissDistance() {
        return missDistance;
    }

}
