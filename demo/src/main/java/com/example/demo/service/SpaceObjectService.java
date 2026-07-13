package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.NearEarthObjects;
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
        List<Asteroid> asteriods = returnAsteroids();

        List<NearEarthObjects> allSpaceObjects = new java.util.ArrayList<>();
        allSpaceObjects.addAll(comets);
        allSpaceObjects.addAll(asteriods);

        return allSpaceObjects;
    }
}
