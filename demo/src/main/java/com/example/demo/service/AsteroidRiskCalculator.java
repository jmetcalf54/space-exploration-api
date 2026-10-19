package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class AsteroidRiskCalculator {

    public int calculateRiskScore(
            boolean potentiallyHazardous,
            double distanceFromEarth) {

        if (!potentiallyHazardous) {
            return 1;
        }

        if (distanceFromEarth < 100_000) {
            return 5;
        } else if (distanceFromEarth < 384_400) {
            return 4;
        } else if (distanceFromEarth < 1_000_000) {
            return 3;
        } else {
            return 2;
        }
    }
}