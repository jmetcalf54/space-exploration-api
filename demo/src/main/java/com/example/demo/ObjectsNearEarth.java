package com.example.demo;

public abstract class ObjectsNearEarth {
    private String officialName;
    private double distanceFromEarth;

    protected ObjectsNearEarth(String officialName, double distanceFromEarth) {
        this.officialName = officialName;
        this.distanceFromEarth = distanceFromEarth;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public double getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public void setDistanceFromEarth(double distanceFromEarth) {
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