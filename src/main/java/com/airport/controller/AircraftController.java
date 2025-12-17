package com.airport.controller;

import com.airport.dto.AircraftDTO;
import com.airport.service.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
@Tag(name = "Aircraft", description = "Aircraft management APIs")
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    @Operation(summary = "Get all aircraft")
    public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get aircraft by ID")
    public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable Long id) {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new aircraft")
    public ResponseEntity<AircraftDTO> createAircraft(@Valid @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO createdAircraft = aircraftService.createAircraft(aircraftDTO);
        return new ResponseEntity<>(createdAircraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an aircraft")
    public ResponseEntity<AircraftDTO> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftDTO aircraftDTO) {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, aircraftDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an aircraft")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }
}
