package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SpaceObjectService;
import com.example.demo.model.Comet;
import com.example.demo.model.Asteroid;
import com.example.demo.model.NearEarthObjects;

import java.util.List;

@RestController
public class SpaceObjectController {

    private final SpaceObjectService spaceObjectService;

    public SpaceObjectController(SpaceObjectService spaceObjectService){
        this.spaceObjectService = spaceObjectService;
    }

    @GetMapping("/comets")
    public List<Comet> cometsAPI(){
        return spaceObjectService.returnComets();
    }

    @GetMapping("/asteroids")
    public List<Asteroid> asteroidsAPI(){
        return spaceObjectService.returnAsteroids();
    }

    @GetMapping("/near-earth-objects")
    public List<NearEarthObjects> spaceObjectsAPI(){
        return spaceObjectService.returnAllSpaceObjects();
    }
}
