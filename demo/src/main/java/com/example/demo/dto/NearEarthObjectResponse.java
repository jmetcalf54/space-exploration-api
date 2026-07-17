package com.example.demo.dto;

import com.example.demo.model.NearEarthObjects;

public class NearEarthObjectResponse {
    private String name;
    private String objectType;
    private String distanceFromEarth;

    public NearEarthObjectResponse(NearEarthObjects nearEarthObject) {
        this.name = nearEarthObject.getOfficialName();
        this.objectType = nearEarthObject.getObjectType();
        this.distanceFromEarth = String.valueOf(nearEarthObject.getDistanceFromEarth());
    }

    public String getName() {
        return name;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getDistanceFromEarth() {
        return distanceFromEarth;
    }

}