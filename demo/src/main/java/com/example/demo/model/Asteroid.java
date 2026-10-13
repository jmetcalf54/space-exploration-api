package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "asteroids")
public class Asteroid extends NearEarthObject {
    private boolean potentiallyHazardous;
    private int threatLevel;

    @Id 
    @GeneratedValue
    private Long id;

    protected Asteroid() {
    }

    public Asteroid(String officialName,
                Double distanceFromEarth,
                int threatLevel,
                boolean potentiallyHazardous) {
    super(officialName, distanceFromEarth);
    this.threatLevel = threatLevel;
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

    public int getThreatLevel() {
        return threatLevel;
    }
}
