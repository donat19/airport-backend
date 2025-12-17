package com.airport.service;

import com.airport.dto.AirportDTO;
import com.airport.exception.DuplicateResourceException;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.Airport;
import com.airport.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(airport -> modelMapper.map(airport, AirportDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));
        return modelMapper.map(airport, AirportDTO.class);
    }

    @Transactional(readOnly = true)
    public AirportDTO getAirportByCode(String code) {
        Airport airport = airportRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "code", code));
        return modelMapper.map(airport, AirportDTO.class);
    }

    @Transactional
    public AirportDTO createAirport(AirportDTO airportDTO) {
        if (airportRepository.existsByCode(airportDTO.getCode())) {
            throw new DuplicateResourceException("Airport", "code", airportDTO.getCode());
        }
        
        Airport airport = modelMapper.map(airportDTO, Airport.class);
        Airport savedAirport = airportRepository.save(airport);
        return modelMapper.map(savedAirport, AirportDTO.class);
    }

    @Transactional
    public AirportDTO updateAirport(Long id, AirportDTO airportDTO) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));
        
        if (!airport.getCode().equals(airportDTO.getCode()) && 
            airportRepository.existsByCode(airportDTO.getCode())) {
            throw new DuplicateResourceException("Airport", "code", airportDTO.getCode());
        }
        
        airport.setCode(airportDTO.getCode());
        airport.setName(airportDTO.getName());
        airport.setCity(airportDTO.getCity());
        airport.setCountry(airportDTO.getCountry());
        airport.setTimezone(airportDTO.getTimezone());
        
        Airport updatedAirport = airportRepository.save(airport);
        return modelMapper.map(updatedAirport, AirportDTO.class);
    }

    @Transactional
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));
        airportRepository.delete(airport);
    }
}
