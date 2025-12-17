package com.airport.service;

import com.airport.dto.AirlineDTO;
import com.airport.exception.DuplicateResourceException;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.Airline;
import com.airport.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AirlineDTO> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(airline -> modelMapper.map(airline, AirlineDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        return modelMapper.map(airline, AirlineDTO.class);
    }

    @Transactional
    public AirlineDTO createAirline(AirlineDTO airlineDTO) {
        if (airlineRepository.existsByCode(airlineDTO.getCode())) {
            throw new DuplicateResourceException("Airline", "code", airlineDTO.getCode());
        }
        
        Airline airline = modelMapper.map(airlineDTO, Airline.class);
        Airline savedAirline = airlineRepository.save(airline);
        return modelMapper.map(savedAirline, AirlineDTO.class);
    }

    @Transactional
    public AirlineDTO updateAirline(Long id, AirlineDTO airlineDTO) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        
        if (!airline.getCode().equals(airlineDTO.getCode()) && 
            airlineRepository.existsByCode(airlineDTO.getCode())) {
            throw new DuplicateResourceException("Airline", "code", airlineDTO.getCode());
        }
        
        airline.setCode(airlineDTO.getCode());
        airline.setName(airlineDTO.getName());
        airline.setCountry(airlineDTO.getCountry());
        
        Airline updatedAirline = airlineRepository.save(airline);
        return modelMapper.map(updatedAirline, AirlineDTO.class);
    }

    @Transactional
    public void deleteAirline(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        airlineRepository.delete(airline);
    }
}
