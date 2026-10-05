package com.example.demo.repository;

import com.example.demo.model.Comet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CometRepository extends JpaRepository<Comet, Long>{
    
}
