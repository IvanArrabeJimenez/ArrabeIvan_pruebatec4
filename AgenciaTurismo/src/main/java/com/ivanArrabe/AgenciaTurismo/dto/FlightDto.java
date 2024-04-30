package com.ivanArrabe.AgenciaTurismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private Long id;
    private String flightCode;
    private String origin;
    private String destination;
    private LocalDate departureDate;
}
