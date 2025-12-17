package com.airport.repository;

import com.airport.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
    
    List<Gate> findByAirportId(Long airportId);
    
    List<Gate> findByAirportCode(String airportCode);
}
