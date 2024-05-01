package com.ivanArrabe.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Hotel hotel;

//    @OneToMany(mappedBy = "room")
//    private List<RoomBooking> roomBookings;

    private String roomCode;
    private String roomType;
    private Integer roomCapacity;
    private Boolean deleted;
}
