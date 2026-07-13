package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SpaceObjectService {
    
    public List<Comet> returnComets() {
        return List.of(
            new Comet("Halley's Comet", 100.0),
            new Comet("Red Rocket", 10000.000)
        );
    }
}
