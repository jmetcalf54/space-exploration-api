package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.demo.model.Comet;

@DataJpaTest
public class CometRepositoryTest {
    @Autowired
    private CometRepository cometRepository;

    @BeforeEach
    void setUp() {
        cometRepository.saveAll(List.of(
            new Comet("Halley's Comet", 100.0, "500km"),
            new Comet("Red Rocket", 10000.000, "1000km")
        ));
    }

    @Test
    void testFindAllComets() {
        List<Comet> comets = cometRepository.findAll();
        assertEquals(2, comets.size());
        assertEquals("Halley's Comet", comets.get(0).getOfficialName());
        assertEquals("500km", comets.get(0).getTailLength());
        assertEquals("Red Rocket", comets.get(1).getOfficialName());
        assertEquals("1000km", comets.get(1).getTailLength());
    }
}
