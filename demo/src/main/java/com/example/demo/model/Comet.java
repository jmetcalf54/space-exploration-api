package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "comets")
public class Comet extends NearEarthObject {
    private String tailLength;

    @Id 
    @GeneratedValue
    private Long id;

    protected Comet() {
    }

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
