package com.example.demo.external.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NasaAsteroidResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("absolute_magnitude_h")
    private Double absoluteMagnitudeH;

    @JsonProperty ("is_potentially_hazardous_asteroid")
    private Boolean isPotentiallyHazardousAsteroid;

    @JsonProperty ("close_approach_data")
    private List<NasaCloseApproachResponse> closeApproachData;

    public String getName() {
        return name;
    }

    public Double getAbsoluteMagnitudeH() {
        return absoluteMagnitudeH;
    }

    public Boolean getIsPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public List<NasaCloseApproachResponse> getCloseApproachData() {
        return closeApproachData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbsoluteMagnitudeH(Double absoluteMagnitudeH) {
        this.absoluteMagnitudeH = absoluteMagnitudeH;
    }

    public void setIsPotentiallyHazardousAsteroid(Boolean isPotentiallyHazardousAsteroid) {
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
    }

    public void setCloseApproachData(List<NasaCloseApproachResponse> closeApproachData) {
        this.closeApproachData = closeApproachData;
    }
    
}
