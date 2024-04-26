package com.ivanArrabe.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private HotelBooking hotelBooking;

    private String roomCode;
    private String roomType;
    private Double pricePerNight;
    private boolean booked = false;
}
