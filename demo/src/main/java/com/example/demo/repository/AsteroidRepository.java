package com.example.demo.repository;

import java.util.List;
import com.example.demo.model.Asteroid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsteroidRepository extends JpaRepository<Asteroid, Long>{
    
    List<Asteroid> findByThreatLevel(int threatLevel);

    List<Asteroid> findByThreatLevelGreaterThanEqual(int threatLevel);

}
