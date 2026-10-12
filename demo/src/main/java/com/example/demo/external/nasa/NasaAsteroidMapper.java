package com.example.demo.external.nasa;

import com.example.demo.external.nasa.dto.NasaAsteroidResponse;

import com.example.demo.model.Asteroid;

public class NasaAsteroidMapper {


    public Asteroid toAsteroid(NasaAsteroidResponse response) {
        
        Double closeApproachDistanceDouble = response.getCloseApproachData().get(0).getMissDistance().getKilometersAsDouble();

        Asteroid asteroid = new Asteroid(response.getName(),closeApproachDistanceDouble,1);

        return asteroid;
    }
    
}
