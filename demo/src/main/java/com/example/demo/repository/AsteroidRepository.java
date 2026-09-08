package com.example.demo.repository;

import com.example.demo.model.Asteroid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsteroidRepository extends JpaRepository<Asteroid, Long>{
    
}
