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
public class RoomBooking extends Booking{

    @ManyToOne
    private Room room;

    private String city;
    private Long hotelId;
    private String roomType;
    private LocalDate entryDate;
    private LocalDate departureDate;
}
