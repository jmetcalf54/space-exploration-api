package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsteroidRiskCalculatorTest {

    @Test
    void testCalculateRiskScore() {
        AsteroidRiskCalculator calculator = new AsteroidRiskCalculator();

        assertEquals(1, calculator.calculateRiskScore(false, 500_000));

        assertEquals(5, calculator.calculateRiskScore(true, 50_000));
        assertEquals(4, calculator.calculateRiskScore(true, 200_000));
        assertEquals(3, calculator.calculateRiskScore(true, 800_000));
        assertEquals(2, calculator.calculateRiskScore(true, 1_500_000));
    }
}