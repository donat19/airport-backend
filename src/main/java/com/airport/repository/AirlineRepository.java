package com.airport.repository;

import com.airport.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    
    Optional<Airline> findByCode(String code);
    
    boolean existsByCode(String code);
}
