package com.example.demo.dto;

import com.example.demo.model.NearEarthObject;

public class NearEarthObjectResponse {
    private String name;
    private String objectType;
    private Double distanceFromEarth;

    public NearEarthObjectResponse(NearEarthObject nearEarthObject) {
        this.name = nearEarthObject.getOfficialName();
        this.objectType = nearEarthObject.getObjectType();
        this.distanceFromEarth = nearEarthObject.getDistanceFromEarth();
    }

    public String getName() {
        return name;
    }

    public String getObjectType() {
        return objectType;
    }

    public Double getDistanceFromEarth() {
        return distanceFromEarth;
    }

}