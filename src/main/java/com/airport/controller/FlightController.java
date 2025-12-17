package com.airport.controller;

import com.airport.dto.FlightDTO;
import com.airport.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flight", description = "Flight management APIs")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    @Operation(summary = "Get all flights")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get flight by ID")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/departures/airport/{airportId}")
    @Operation(summary = "Get departures by airport")
    public ResponseEntity<List<FlightDTO>> getDeparturesByAirport(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getDeparturesByAirport(airportId));
    }

    @GetMapping("/arrivals/airport/{airportId}")
    @Operation(summary = "Get arrivals by airport")
    public ResponseEntity<List<FlightDTO>> getArrivalsByAirport(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getArrivalsByAirport(airportId));
    }

    @GetMapping("/departures/airport/{airportId}/timerange")
    @Operation(summary = "Get departures by airport and time range")
    public ResponseEntity<List<FlightDTO>> getDeparturesByAirportAndTimeRange(
            @PathVariable Long airportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseEntity.ok(
            flightService.getDeparturesByAirportAndTimeRange(airportId, startTime, endTime));
    }

    @GetMapping("/arrivals/airport/{airportId}/timerange")
    @Operation(summary = "Get arrivals by airport and time range")
    public ResponseEntity<List<FlightDTO>> getArrivalsByAirportAndTimeRange(
            @PathVariable Long airportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseEntity.ok(
            flightService.getArrivalsByAirportAndTimeRange(airportId, startTime, endTime));
    }

    @PostMapping
    @Operation(summary = "Create a new flight")
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightService.createFlight(flightDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a flight")
    public ResponseEntity<FlightDTO> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a flight")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
