package com.example.demo.model;

public class Comet extends ObjectsNearEarth {

    public Comet(String officialName, Double distanceFromEarth){
        super(officialName, distanceFromEarth);
    }

    @Override
    public String getObjectType(){
        return "Comet";
    }
}
