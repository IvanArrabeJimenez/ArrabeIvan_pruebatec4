package com.ivanArrabe.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String flightCode;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private Integer seatsEconomy;
    private Integer seatsBusiness;
    private Boolean deleted;
}
