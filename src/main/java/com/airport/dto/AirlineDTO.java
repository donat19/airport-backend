package com.airport.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDTO {
    
    private Long id;
    
    @NotBlank(message = "Airline code is required")
    private String code;
    
    @NotBlank(message = "Airline name is required")
    private String name;
    
    private String country;
}
