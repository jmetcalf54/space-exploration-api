package com.example.demo.model;

public class Asteroid extends NearEarthObject {
    private int threatLevel;

    public Asteroid(String officialName, Double distanceFromEarth, int threatLevel){
        super(officialName, distanceFromEarth);
        this.threatLevel = threatLevel;
    }

    @Override
    public String getObjectType(){
        return "Asteroid";
    }

    public int getThreatLevel() {
        return threatLevel;
    }

}
