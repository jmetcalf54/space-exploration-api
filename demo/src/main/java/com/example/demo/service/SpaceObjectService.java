package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.NearEarthObjects;
import com.example.demo.dto.NearEarthObjectResponse;
import com.example.demo.model.Asteroid;
import com.example.demo.model.Comet;

@Service
public class SpaceObjectService {
    
    public List<Comet> returnComets() {
        return List.of(
            new Comet("Halley's Comet", 100.0, "500km"),
            new Comet("Red Rocket", 10000.000, "1000km")
        );
    }

    public List<Asteroid> returnAsteroids() {
        return List.of(
            new Asteroid("X-1002342", 100.0,1),
            new Asteroid("Devastator", 10000.000,2)
        );
    }

    public List<NearEarthObjects> returnAllSpaceObjects() {
        List<Comet> comets = returnComets();
        List<Asteroid> asteroids = returnAsteroids();

        List<NearEarthObjects> allSpaceObjects = new java.util.ArrayList<>();
        allSpaceObjects.addAll(comets);
        allSpaceObjects.addAll(asteroids);

        return allSpaceObjects;
    }

    public NearEarthObjects returnClosestSpaceObject() {
        List<NearEarthObjects> allSpaceObjects = returnAllSpaceObjects();
        NearEarthObjects closest = allSpaceObjects.get(0);
        for (NearEarthObjects obj : allSpaceObjects) {
            if (obj.getDistanceFromEarth() < closest.getDistanceFromEarth()) {
                closest = obj;
            }
        }
        return closest;
    }

    public List<Asteroid> returnAsteroidsByThreatLevel(int level) {
        return returnAsteroids().stream()
                .filter(asteroid -> asteroid.getThreatLevel() == level)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Asteroid> returnMinThreatLevelAsteroids(int level) {
        return returnAsteroids().stream()
                .filter(asteroid -> asteroid.getThreatLevel() >= level)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<NearEarthObjectResponse> returnAllSpaceObjectsAsResponse() {
        List<NearEarthObjects> spaceObjects = returnAllSpaceObjects();
        List<NearEarthObjectResponse> responses = new ArrayList<>();
        for (NearEarthObjects spaceObject : spaceObjects) {
            NearEarthObjectResponse response = new NearEarthObjectResponse(spaceObject);
            responses.add(response);
        }
        return responses;
    }
}
