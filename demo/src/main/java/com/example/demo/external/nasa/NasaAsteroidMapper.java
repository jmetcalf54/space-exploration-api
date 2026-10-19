package com.example.demo.external.nasa;

import org.springframework.stereotype.Component;

import com.example.demo.external.nasa.dto.NasaAsteroidResponse;
import com.example.demo.model.Asteroid;
import com.example.demo.service.AsteroidRiskCalculator;

@Component
public class NasaAsteroidMapper {

    private final AsteroidRiskCalculator riskCalculator;

    public NasaAsteroidMapper(AsteroidRiskCalculator riskCalculator) {
        this.riskCalculator = riskCalculator;
    }

    public Asteroid toAsteroid(NasaAsteroidResponse response) {
        Double closeApproachDistance = response
                .getCloseApproachData()
                .get(0)
                .getMissDistance()
                .getKilometersAsDouble();

        boolean potentiallyHazardous =
                response.getIsPotentiallyHazardousAsteroid();

        int riskScore = riskCalculator.calculateRiskScore(
                potentiallyHazardous,
                closeApproachDistance
        );

        return new Asteroid(
                response.getName(),
                closeApproachDistance,
                riskScore,
                potentiallyHazardous
        );
    }
}