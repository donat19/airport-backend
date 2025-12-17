package com.airport.controller;

import com.airport.dto.AirportDTO;
import com.airport.service.AirportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Autowired
    private ObjectMapper objectMapper;

    private AirportDTO airportDTO;

    @BeforeEach
    void setUp() {
        airportDTO = new AirportDTO();
        airportDTO.setId(1L);
        airportDTO.setCode("JFK");
        airportDTO.setName("John F. Kennedy International Airport");
        airportDTO.setCity("New York");
        airportDTO.setCountry("USA");
        airportDTO.setTimezone("America/New_York");
    }

    @Test
    void getAllAirports_ShouldReturnAirportList() throws Exception {
        // Given
        List<AirportDTO> airports = Arrays.asList(airportDTO);
        when(airportService.getAllAirports()).thenReturn(airports);

        // When & Then
        mockMvc.perform(get("/api/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("JFK"))
                .andExpect(jsonPath("$[0].name").value("John F. Kennedy International Airport"));

        verify(airportService).getAllAirports();
    }

    @Test
    void getAirportById_ShouldReturnAirport() throws Exception {
        // Given
        when(airportService.getAirportById(1L)).thenReturn(airportDTO);

        // When & Then
        mockMvc.perform(get("/api/airports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JFK"))
                .andExpect(jsonPath("$.name").value("John F. Kennedy International Airport"));

        verify(airportService).getAirportById(1L);
    }

    @Test
    void createAirport_ShouldReturnCreatedAirport() throws Exception {
        // Given
        when(airportService.createAirport(any(AirportDTO.class))).thenReturn(airportDTO);

        // When & Then
        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("JFK"));

        verify(airportService).createAirport(any(AirportDTO.class));
    }

    @Test
    void updateAirport_ShouldReturnUpdatedAirport() throws Exception {
        // Given
        when(airportService.updateAirport(eq(1L), any(AirportDTO.class))).thenReturn(airportDTO);

        // When & Then
        mockMvc.perform(put("/api/airports/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airportDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JFK"));

        verify(airportService).updateAirport(eq(1L), any(AirportDTO.class));
    }

    @Test
    void deleteAirport_ShouldReturnNoContent() throws Exception {
        // Given
        doNothing().when(airportService).deleteAirport(1L);

        // When & Then
        mockMvc.perform(delete("/api/airports/1"))
                .andExpect(status().isNoContent());

        verify(airportService).deleteAirport(1L);
    }
}
