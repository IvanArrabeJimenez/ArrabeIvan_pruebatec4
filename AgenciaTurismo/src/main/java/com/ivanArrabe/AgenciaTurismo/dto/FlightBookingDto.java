package com.ivanArrabe.AgenciaTurismo.dto;

import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingDto {

    private Long id;
    private User user;
    private Flight flight;
}
