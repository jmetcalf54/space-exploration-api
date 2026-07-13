package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class SpaceObjectController {

    @GetMapping("/comets")
    public List<Comet> returnComets() {
        return List.of(
            new Comet("Halley's Comet", 100.0),
            new Comet("Red Rocket", 10000.000)
        );
    }
}
