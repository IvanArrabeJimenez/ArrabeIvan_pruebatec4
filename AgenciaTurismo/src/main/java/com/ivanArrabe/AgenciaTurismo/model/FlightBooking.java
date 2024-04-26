package com.ivanArrabe.AgenciaTurismo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightBooking extends Booking{

    @ManyToOne
    private Flight flight;

    private String origin;
    private String destination;
    private LocalDate departureDate;
    private Integer seatsEconomyTaken;
    private Integer seatsBusinessTaken;
}
