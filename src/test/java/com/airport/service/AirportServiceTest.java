package com.airport.service;

import com.airport.dto.AirportDTO;
import com.airport.exception.DuplicateResourceException;
import com.airport.exception.ResourceNotFoundException;
import com.airport.model.Airport;
import com.airport.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AirportService airportService;

    private Airport airport;
    private AirportDTO airportDTO;

    @BeforeEach
    void setUp() {
        airport = new Airport();
        airport.setId(1L);
        airport.setCode("JFK");
        airport.setName("John F. Kennedy International Airport");
        airport.setCity("New York");
        airport.setCountry("USA");
        airport.setTimezone("America/New_York");

        airportDTO = new AirportDTO();
        airportDTO.setId(1L);
        airportDTO.setCode("JFK");
        airportDTO.setName("John F. Kennedy International Airport");
        airportDTO.setCity("New York");
        airportDTO.setCountry("USA");
        airportDTO.setTimezone("America/New_York");
    }

    @Test
    void getAllAirports_ShouldReturnListOfAirports() {
        // Given
        List<Airport> airports = Arrays.asList(airport);
        when(airportRepository.findAll()).thenReturn(airports);
        when(modelMapper.map(airport, AirportDTO.class)).thenReturn(airportDTO);

        // When
        List<AirportDTO> result = airportService.getAllAirports();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("JFK");
        verify(airportRepository).findAll();
    }

    @Test
    void getAirportById_WhenExists_ShouldReturnAirport() {
        // Given
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(modelMapper.map(airport, AirportDTO.class)).thenReturn(airportDTO);

        // When
        AirportDTO result = airportService.getAirportById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("JFK");
        verify(airportRepository).findById(1L);
    }

    @Test
    void getAirportById_WhenNotExists_ShouldThrowException() {
        // Given
        when(airportRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> airportService.getAirportById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Airport not found");
    }

    @Test
    void createAirport_WhenCodeNotExists_ShouldCreateAirport() {
        // Given
        when(airportRepository.existsByCode("JFK")).thenReturn(false);
        when(modelMapper.map(airportDTO, Airport.class)).thenReturn(airport);
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        when(modelMapper.map(airport, AirportDTO.class)).thenReturn(airportDTO);

        // When
        AirportDTO result = airportService.createAirport(airportDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("JFK");
        verify(airportRepository).save(any(Airport.class));
    }

    @Test
    void createAirport_WhenCodeExists_ShouldThrowException() {
        // Given
        when(airportRepository.existsByCode("JFK")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> airportService.createAirport(airportDTO))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Airport already exists");
    }

    @Test
    void updateAirport_WhenExists_ShouldUpdateAirport() {
        // Given
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        when(modelMapper.map(airport, AirportDTO.class)).thenReturn(airportDTO);

        // When
        AirportDTO result = airportService.updateAirport(1L, airportDTO);

        // Then
        assertThat(result).isNotNull();
        verify(airportRepository).save(any(Airport.class));
    }

    @Test
    void deleteAirport_WhenExists_ShouldDeleteAirport() {
        // Given
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        // When
        airportService.deleteAirport(1L);

        // Then
        verify(airportRepository).delete(airport);
    }
}
