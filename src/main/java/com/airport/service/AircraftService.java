package com.airport.service;

import com.airport.dto.AircraftDTO;
import com.airport.exception.DuplicateResourceException;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.Aircraft;
import com.airport.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll().stream()
                .map(aircraft -> modelMapper.map(aircraft, AircraftDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
        return modelMapper.map(aircraft, AircraftDTO.class);
    }

    @Transactional
    public AircraftDTO createAircraft(AircraftDTO aircraftDTO) {
        if (aircraftRepository.existsByRegistrationNumber(aircraftDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Aircraft", "registrationNumber", 
                aircraftDTO.getRegistrationNumber());
        }
        
        Aircraft aircraft = modelMapper.map(aircraftDTO, Aircraft.class);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return modelMapper.map(savedAircraft, AircraftDTO.class);
    }

    @Transactional
    public AircraftDTO updateAircraft(Long id, AircraftDTO aircraftDTO) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
        
        if (!aircraft.getRegistrationNumber().equals(aircraftDTO.getRegistrationNumber()) && 
            aircraftRepository.existsByRegistrationNumber(aircraftDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Aircraft", "registrationNumber", 
                aircraftDTO.getRegistrationNumber());
        }
        
        aircraft.setRegistrationNumber(aircraftDTO.getRegistrationNumber());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setManufacturer(aircraftDTO.getManufacturer());
        aircraft.setCapacity(aircraftDTO.getCapacity());
        
        Aircraft updatedAircraft = aircraftRepository.save(aircraft);
        return modelMapper.map(updatedAircraft, AircraftDTO.class);
    }

    @Transactional
    public void deleteAircraft(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
        aircraftRepository.delete(aircraft);
    }
}
