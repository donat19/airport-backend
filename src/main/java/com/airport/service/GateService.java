package com.airport.service;

import com.airport.dto.GateDTO;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.Airport;
import com.airport.model.Gate;
import com.airport.repository.AirportRepository;
import com.airport.repository.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GateService {

    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    @Transactional(readOnly = true)
    public List<GateDTO> getAllGates() {
        return gateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GateDTO getGateById(Long id) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate", "id", id));
        return convertToDTO(gate);
    }

    @Transactional(readOnly = true)
    public List<GateDTO> getGatesByAirportId(Long airportId) {
        return gateRepository.findByAirportId(airportId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GateDTO createGate(GateDTO gateDTO) {
        Airport airport = airportRepository.findById(gateDTO.getAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", gateDTO.getAirportId()));
        
        Gate gate = new Gate();
        gate.setGateNumber(gateDTO.getGateNumber());
        gate.setTerminal(gateDTO.getTerminal());
        gate.setStatus(gateDTO.getStatus() != null ? gateDTO.getStatus() : Gate.GateStatus.AVAILABLE);
        gate.setAirport(airport);
        
        Gate savedGate = gateRepository.save(gate);
        return convertToDTO(savedGate);
    }

    @Transactional
    public GateDTO updateGate(Long id, GateDTO gateDTO) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate", "id", id));
        
        Airport airport = airportRepository.findById(gateDTO.getAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", gateDTO.getAirportId()));
        
        gate.setGateNumber(gateDTO.getGateNumber());
        gate.setTerminal(gateDTO.getTerminal());
        gate.setStatus(gateDTO.getStatus());
        gate.setAirport(airport);
        
        Gate updatedGate = gateRepository.save(gate);
        return convertToDTO(updatedGate);
    }

    @Transactional
    public void deleteGate(Long id) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate", "id", id));
        gateRepository.delete(gate);
    }

    private GateDTO convertToDTO(Gate gate) {
        GateDTO dto = new GateDTO();
        dto.setId(gate.getId());
        dto.setGateNumber(gate.getGateNumber());
        dto.setTerminal(gate.getTerminal());
        dto.setStatus(gate.getStatus());
        dto.setAirportId(gate.getAirport().getId());
        dto.setAirportCode(gate.getAirport().getCode());
        return dto;
    }
}
