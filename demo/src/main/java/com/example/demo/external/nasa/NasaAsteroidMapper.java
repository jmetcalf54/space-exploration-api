package com.example.demo.external.nasa;

import com.example.demo.external.nasa.dto.NasaAsteroidResponse;

import com.example.demo.model.Asteroid;

public class NasaAsteroidMapper {

    public Asteroid toAsteroid(NasaAsteroidResponse response) {
        Double closeApproachDistance = response
                .getCloseApproachData()
                .get(0)
                .getMissDistance()
                .getKilometersAsDouble();

        return new Asteroid(
                response.getName(),
                closeApproachDistance,
                3,
                response.getIsPotentiallyHazardousAsteroid()
        );
    }

}
