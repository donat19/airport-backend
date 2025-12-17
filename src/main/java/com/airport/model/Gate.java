package com.airport.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Gate number is required")
    @Column(nullable = false)
    private String gateNumber;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GateStatus status = GateStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    @OneToMany(mappedBy = "gate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> flights = new ArrayList<>();

    public enum GateStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE
    }
}
