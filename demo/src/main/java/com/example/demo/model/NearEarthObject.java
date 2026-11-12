package com.example.demo.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NearEarthObject {
    private String officialName;
    private Double distanceFromEarth;

    protected NearEarthObject() {
    }

    protected NearEarthObject(String officialName, Double distanceFromEarth) {
        this.officialName = officialName;
        this.distanceFromEarth = distanceFromEarth;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public Double getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public void setDistanceFromEarth(Double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
    }

    public abstract String getObjectType();
}