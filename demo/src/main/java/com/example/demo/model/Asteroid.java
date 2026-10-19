package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "asteroids")
public class Asteroid extends NearEarthObject {
    private boolean potentiallyHazardous;
    private int riskScore;

    @Id 
    @GeneratedValue
    private Long id;

    protected Asteroid() {
    }

    public Asteroid(String officialName,
                Double distanceFromEarth,
                int riskScore,
                boolean potentiallyHazardous) {
    super(officialName, distanceFromEarth);
    this.riskScore = riskScore;
    this.potentiallyHazardous = potentiallyHazardous;
}

    @Override
    public String getObjectType(){
        return "Asteroid";
    }

    public boolean getIsPotentiallyHazardousAsteroid() {
        return potentiallyHazardous;
    }

    public Long getId() {
        return id;
    }

    public int getRiskScore() {
        return riskScore;
    }
}
