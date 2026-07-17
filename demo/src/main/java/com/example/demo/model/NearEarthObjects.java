package com.example.demo.model;

public abstract class NearEarthObjects {
    private String officialName;
    private Double distanceFromEarth;

    protected NearEarthObjects(String officialName, Double distanceFromEarth) {
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

    public void printOfficialName() {
        System.out.println(getOfficialName());
    }

    public void printDistanceFromEarth() {
        System.out.println(getDistanceFromEarth());
    }

    public abstract String getObjectType();
}