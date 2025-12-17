package com.airport.controller;

import com.airport.dto.AirportDTO;
import com.airport.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
@Tag(name = "Airport", description = "Airport management APIs")
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    @Operation(summary = "Get all airports")
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get airport by ID")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get airport by code")
    public ResponseEntity<AirportDTO> getAirportByCode(@PathVariable String code) {
        return ResponseEntity.ok(airportService.getAirportByCode(code));
    }

    @PostMapping
    @Operation(summary = "Create a new airport")
    public ResponseEntity<AirportDTO> createAirport(@Valid @RequestBody AirportDTO airportDTO) {
        AirportDTO createdAirport = airportService.createAirport(airportDTO);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an airport")
    public ResponseEntity<AirportDTO> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportDTO airportDTO) {
        return ResponseEntity.ok(airportService.updateAirport(id, airportDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an airport")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
