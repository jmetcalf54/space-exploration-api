package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.AsteroidRepository;
import com.example.demo.repository.CometRepository;

import com.example.demo.model.NearEarthObject;
import com.example.demo.model.Asteroid;
import com.example.demo.model.Comet;

import com.example.demo.dto.NearEarthObjectResponse;

@Service
public class SpaceObjectService {
    private final AsteroidRepository asteroidRepository;
    private final CometRepository cometRepository;

    public SpaceObjectService(AsteroidRepository asteroidRepository, CometRepository cometRepository) {
        this.asteroidRepository = asteroidRepository;
        this.cometRepository = cometRepository;
    }
    
    public List<Comet> returnComets() {
        return cometRepository.findAll();
    }

    public List<Asteroid> returnAsteroids() {
        return asteroidRepository.findAll();
    }

    public List<NearEarthObject> returnAllSpaceObjects() {
        List<Comet> comets = returnComets();
        List<Asteroid> asteroids = returnAsteroids();

        List<NearEarthObject> allSpaceObjects = new java.util.ArrayList<>();
        allSpaceObjects.addAll(comets);
        allSpaceObjects.addAll(asteroids);

        return allSpaceObjects;
    }

    public List<NearEarthObjectResponse> returnAllSpaceObjectsAsResponse() {
        List<NearEarthObject> spaceObjects = returnAllSpaceObjects();
        List<NearEarthObjectResponse> responses = new ArrayList<>();
        for (NearEarthObject spaceObject : spaceObjects) {
            NearEarthObjectResponse response = new NearEarthObjectResponse(spaceObject);
            responses.add(response);
        }
        return responses;
    }

    public NearEarthObject returnClosestSpaceObject() {
        List<NearEarthObject> allSpaceObjects = returnAllSpaceObjects();

        if (allSpaceObjects.isEmpty()) {
            throw new IllegalStateException("No near-Earth objects are available.");
        }

        NearEarthObject closest = allSpaceObjects.get(0);

        for (NearEarthObject obj : allSpaceObjects) {
            if (obj.getDistanceFromEarth() < closest.getDistanceFromEarth()) {
                closest = obj;
            }
        }

        return closest;
    }

    public NearEarthObjectResponse returnClosestSpaceObjectAsResponse() {
        NearEarthObject closest = returnClosestSpaceObject();
        NearEarthObjectResponse response = new NearEarthObjectResponse(closest);
        return response;
    }

    public List<Asteroid> returnAsteroidsByRiskScore(int level) {
        validateRiskScore(level);
        return asteroidRepository.findByRiskScore(level);
    }

    public List<Asteroid> returnMinRiskScoreAsteroids(int level) {
        validateRiskScore(level);
        return asteroidRepository.findByRiskScoreGreaterThanEqual(level);
    }

    private void validateRiskScore(int level) {
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("Risk score must be between 1 and 5.");
        }
    }
}
