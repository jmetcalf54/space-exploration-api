package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Comet;

@Service
public class SpaceObjectService {
    
    public List<Comet> returnComets() {
        return List.of(
            new Comet("Halley's Comet", 100.0),
            new Comet("Red Rocket", 10000.000)
        );
    }
}
