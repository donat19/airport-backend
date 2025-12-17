package com.airport.dto;

import com.airport.model.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    
    private Long id;
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
    
    @NotNull(message = "Departure time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureTime;
    
    @NotNull(message = "Arrival time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;
    
    private Flight.FlightStatus status;
    
    @NotNull(message = "Departure airport ID is required")
    private Long departureAirportId;
    
    private String departureAirportCode;
    private String departureAirportName;
    
    @NotNull(message = "Arrival airport ID is required")
    private Long arrivalAirportId;
    
    private String arrivalAirportCode;
    private String arrivalAirportName;
    
    @NotNull(message = "Airline ID is required")
    private Long airlineId;
    
    private String airlineCode;
    private String airlineName;
    
    private Long aircraftId;
    private String aircraftRegistration;
    
    private Long gateId;
    private String gateNumber;
}
