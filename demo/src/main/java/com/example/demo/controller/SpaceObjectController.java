package com.example.demo.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.SpaceObjectService;
import com.example.demo.model.Comet;
import com.example.demo.model.Asteroid;
import com.example.demo.dto.NearEarthObjectResponse;

import java.util.List;

@RestController
public class SpaceObjectController {

    private final SpaceObjectService spaceObjectService;

    public SpaceObjectController(SpaceObjectService spaceObjectService){
        this.spaceObjectService = spaceObjectService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        ResponseEntity<String> responseEntity = ResponseEntity.badRequest().body(e.getMessage());

        return responseEntity;
    }

    @GetMapping("/api/comets")
    public List<Comet> cometsAPI(){
        return spaceObjectService.returnComets();
    }

    @GetMapping("/api/asteroids")
    public List<Asteroid> asteroidsAPI(
    @RequestParam(name = "minThreatLevel", required = false) Integer minThreatLevel) {
        if (minThreatLevel != null) {
            return spaceObjectService.returnMinThreatLevelAsteroids(minThreatLevel);
        }
        return spaceObjectService.returnAsteroids();
    }

    @GetMapping("/api/near-earth-objects")
    public List<NearEarthObjectResponse> spaceObjectsAPI(){
        return spaceObjectService.returnAllSpaceObjectsAsResponse();
    }

    @GetMapping("/api/near-earth-objects/closest")
    public NearEarthObjectResponse closestSpaceObjectAPI(){
        return spaceObjectService.returnClosestSpaceObjectAsResponse();
    }

    @GetMapping("/api/asteroids/threat/{level}")
    public List<Asteroid> asteroidsByThreatLevelAPI(@PathVariable int level){
        return spaceObjectService.returnAsteroidsByThreatLevel(level);
    }
}
