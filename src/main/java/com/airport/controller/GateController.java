package com.airport.controller;

import com.airport.dto.GateDTO;
import com.airport.service.GateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
@RequiredArgsConstructor
@Tag(name = "Gate", description = "Gate management APIs")
public class GateController {

    private final GateService gateService;

    @GetMapping
    @Operation(summary = "Get all gates")
    public ResponseEntity<List<GateDTO>> getAllGates() {
        return ResponseEntity.ok(gateService.getAllGates());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get gate by ID")
    public ResponseEntity<GateDTO> getGateById(@PathVariable Long id) {
        return ResponseEntity.ok(gateService.getGateById(id));
    }

    @GetMapping("/airport/{airportId}")
    @Operation(summary = "Get gates by airport")
    public ResponseEntity<List<GateDTO>> getGatesByAirportId(@PathVariable Long airportId) {
        return ResponseEntity.ok(gateService.getGatesByAirportId(airportId));
    }

    @PostMapping
    @Operation(summary = "Create a new gate")
    public ResponseEntity<GateDTO> createGate(@Valid @RequestBody GateDTO gateDTO) {
        GateDTO createdGate = gateService.createGate(gateDTO);
        return new ResponseEntity<>(createdGate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a gate")
    public ResponseEntity<GateDTO> updateGate(
            @PathVariable Long id,
            @Valid @RequestBody GateDTO gateDTO) {
        return ResponseEntity.ok(gateService.updateGate(id, gateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a gate")
    public ResponseEntity<Void> deleteGate(@PathVariable Long id) {
        gateService.deleteGate(id);
        return ResponseEntity.noContent().build();
    }
}
