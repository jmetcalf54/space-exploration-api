package com.example.demo.model;

public class Comet extends NearEarthObject {
    private String tailLength;

    public Comet(String officialName, Double distanceFromEarth, String tailLength){
        super(officialName, distanceFromEarth);
        this.tailLength = tailLength;
    }

    @Override
    public String getObjectType(){
        return "Comet";
    }

    public String getTailLength() {
        return tailLength;
    }
    
}
