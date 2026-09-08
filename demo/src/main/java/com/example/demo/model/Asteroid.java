package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "asteroids")
public class Asteroid extends NearEarthObject {
    private int threatLevel;

    @Id 
    @GeneratedValue
    private Long id;

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

    public Long getId() {
        return id;
    }

}
