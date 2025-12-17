package com.airport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {
    
    private Long id;
    
    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 3, message = "Airport code must be 3 characters")
    private String code;
    
    @NotBlank(message = "Airport name is required")
    private String name;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "Country is required")
    private String country;
    
    private String timezone;
}
