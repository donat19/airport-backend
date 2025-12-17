package com.airport.service;

import com.airport.dto.FlightDTO;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.*;
import com.airport.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final GateRepository gateRepository;

    @Transactional(readOnly = true)
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        return convertToDTO(flight);
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getDeparturesByAirport(Long airportId) {
        return flightRepository.findByDepartureAirportId(airportId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getArrivalsByAirport(Long airportId) {
        return flightRepository.findByArrivalAirportId(airportId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getDeparturesByAirportAndTimeRange(
            Long airportId, LocalDateTime startTime, LocalDateTime endTime) {
        return flightRepository.findDeparturesByAirportAndTimeRange(airportId, startTime, endTime)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FlightDTO> getArrivalsByAirportAndTimeRange(
            Long airportId, LocalDateTime startTime, LocalDateTime endTime) {
        return flightRepository.findArrivalsByAirportAndTimeRange(airportId, startTime, endTime)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setStatus(flightDTO.getStatus() != null ? flightDTO.getStatus() : Flight.FlightStatus.SCHEDULED);
        
        Airport departureAirport = airportRepository.findById(flightDTO.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", flightDTO.getDepartureAirportId()));
        flight.setDepartureAirport(departureAirport);
        
        Airport arrivalAirport = airportRepository.findById(flightDTO.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", flightDTO.getArrivalAirportId()));
        flight.setArrivalAirport(arrivalAirport);
        
        Airline airline = airlineRepository.findById(flightDTO.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", flightDTO.getAirlineId()));
        flight.setAirline(airline);
        
        if (flightDTO.getAircraftId() != null) {
            Aircraft aircraft = aircraftRepository.findById(flightDTO.getAircraftId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", flightDTO.getAircraftId()));
            flight.setAircraft(aircraft);
        }
        
        if (flightDTO.getGateId() != null) {
            Gate gate = gateRepository.findById(flightDTO.getGateId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gate", "id", flightDTO.getGateId()));
            flight.setGate(gate);
        }
        
        Flight savedFlight = flightRepository.save(flight);
        return convertToDTO(savedFlight);
    }

    @Transactional
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setStatus(flightDTO.getStatus());
        
        Airport departureAirport = airportRepository.findById(flightDTO.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", flightDTO.getDepartureAirportId()));
        flight.setDepartureAirport(departureAirport);
        
        Airport arrivalAirport = airportRepository.findById(flightDTO.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", flightDTO.getArrivalAirportId()));
        flight.setArrivalAirport(arrivalAirport);
        
        Airline airline = airlineRepository.findById(flightDTO.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", flightDTO.getAirlineId()));
        flight.setAirline(airline);
        
        if (flightDTO.getAircraftId() != null) {
            Aircraft aircraft = aircraftRepository.findById(flightDTO.getAircraftId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", flightDTO.getAircraftId()));
            flight.setAircraft(aircraft);
        } else {
            flight.setAircraft(null);
        }
        
        if (flightDTO.getGateId() != null) {
            Gate gate = gateRepository.findById(flightDTO.getGateId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gate", "id", flightDTO.getGateId()));
            flight.setGate(gate);
        } else {
            flight.setGate(null);
        }
        
        Flight updatedFlight = flightRepository.save(flight);
        return convertToDTO(updatedFlight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        flightRepository.delete(flight);
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setStatus(flight.getStatus());
        
        dto.setDepartureAirportId(flight.getDepartureAirport().getId());
        dto.setDepartureAirportCode(flight.getDepartureAirport().getCode());
        dto.setDepartureAirportName(flight.getDepartureAirport().getName());
        
        dto.setArrivalAirportId(flight.getArrivalAirport().getId());
        dto.setArrivalAirportCode(flight.getArrivalAirport().getCode());
        dto.setArrivalAirportName(flight.getArrivalAirport().getName());
        
        dto.setAirlineId(flight.getAirline().getId());
        dto.setAirlineCode(flight.getAirline().getCode());
        dto.setAirlineName(flight.getAirline().getName());
        
        if (flight.getAircraft() != null) {
            dto.setAircraftId(flight.getAircraft().getId());
            dto.setAircraftRegistration(flight.getAircraft().getRegistrationNumber());
        }
        
        if (flight.getGate() != null) {
            dto.setGateId(flight.getGate().getId());
            dto.setGateNumber(flight.getGate().getGateNumber());
        }
        
        return dto;
    }
}
