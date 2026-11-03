package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.SpaceObjectService;
import com.example.demo.model.Comet;
import com.example.demo.model.Asteroid;
import com.example.demo.dto.NearEarthObjectResponse;
import com.example.demo.service.NasaAsteroidImportService;

import java.util.List;

@RestController
public class SpaceObjectController {

    private final SpaceObjectService spaceObjectService;
    private final NasaAsteroidImportService nasaAsteroidImportService;

    public SpaceObjectController(SpaceObjectService spaceObjectService, NasaAsteroidImportService nasaAsteroidImportService){
        this.spaceObjectService = spaceObjectService;
        this.nasaAsteroidImportService = nasaAsteroidImportService;
    }

    @GetMapping("/api/comets")
    public List<Comet> cometsAPI(){
        return spaceObjectService.returnComets();
    }

    @GetMapping("/api/asteroids")
    public List<Asteroid> asteroidsAPI(
            @RequestParam(name = "minRiskScore", required = false) Integer minRiskScore) {

        if (minRiskScore != null) {
            return spaceObjectService.returnMinRiskScoreAsteroids(minRiskScore);
        }

        return spaceObjectService.returnAsteroids();
    }

    @PostMapping("/api/asteroids/import")
    public List<Asteroid> importAsteroids() {
        return nasaAsteroidImportService.importAsteroids();
    }

    @GetMapping("/api/near-earth-objects")
    public List<NearEarthObjectResponse> spaceObjectsAPI(){
        return spaceObjectService.returnAllSpaceObjectsAsResponse();
    }

    @GetMapping("/api/near-earth-objects/closest")
    public NearEarthObjectResponse closestSpaceObjectAPI(){
        return spaceObjectService.returnClosestSpaceObjectAsResponse();
    }

    @GetMapping("/api/asteroids/risk/{score}")
    public List<Asteroid> asteroidsByRiskScoreAPI(@PathVariable int score) {
        return spaceObjectService.returnAsteroidsByRiskScore(score);
    }
}
