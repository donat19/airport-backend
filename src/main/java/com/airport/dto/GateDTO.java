package com.airport.dto;

import com.airport.model.Gate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateDTO {
    
    private Long id;
    
    @NotBlank(message = "Gate number is required")
    private String gateNumber;
    
    private String terminal;
    
    private Gate.GateStatus status;
    
    @NotNull(message = "Airport ID is required")
    private Long airportId;
    
    private String airportCode;
}
