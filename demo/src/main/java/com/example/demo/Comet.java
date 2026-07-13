package com.example.demo;

class Comet extends ObjectsNearEarth {

    Comet(String officialName, Double distanceFromEarth){
        super(officialName, distanceFromEarth);
    }

    @Override
    public String getObjectType(){
        return "Comet";
    }
}
