package com.example.demo.external.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NasaNeoBrowseResponse {

    @JsonProperty("near_earth_objects")
    private List<NasaAsteroidResponse> nearEarthObjects;

    public List<NasaAsteroidResponse> getNearEarthObjects() {
        return nearEarthObjects;
    }

    public void setNearEarthObjects(List<NasaAsteroidResponse> nearEarthObjects) {
        this.nearEarthObjects = nearEarthObjects;
    }

}
