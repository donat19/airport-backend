package com.airport.controller;

import com.airport.dto.AirlineDTO;
import com.airport.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
@Tag(name = "Airline", description = "Airline management APIs")
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping
    @Operation(summary = "Get all airlines")
    public ResponseEntity<List<AirlineDTO>> getAllAirlines() {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get airline by ID")
    public ResponseEntity<AirlineDTO> getAirlineById(@PathVariable Long id) {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new airline")
    public ResponseEntity<AirlineDTO> createAirline(@Valid @RequestBody AirlineDTO airlineDTO) {
        AirlineDTO createdAirline = airlineService.createAirline(airlineDTO);
        return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an airline")
    public ResponseEntity<AirlineDTO> updateAirline(
            @PathVariable Long id,
            @Valid @RequestBody AirlineDTO airlineDTO) {
        return ResponseEntity.ok(airlineService.updateAirline(id, airlineDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an airline")
    public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }
}
