package com.airport.repository;

import com.airport.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    List<Flight> findByDepartureAirportId(Long airportId);
    
    List<Flight> findByArrivalAirportId(Long airportId);
    
    List<Flight> findByDepartureAirportCode(String airportCode);
    
    List<Flight> findByArrivalAirportCode(String airportCode);
    
    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :airportId " +
           "AND f.departureTime BETWEEN :startTime AND :endTime")
    List<Flight> findDeparturesByAirportAndTimeRange(
        @Param("airportId") Long airportId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    @Query("SELECT f FROM Flight f WHERE f.arrivalAirport.id = :airportId " +
           "AND f.arrivalTime BETWEEN :startTime AND :endTime")
    List<Flight> findArrivalsByAirportAndTimeRange(
        @Param("airportId") Long airportId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
}
