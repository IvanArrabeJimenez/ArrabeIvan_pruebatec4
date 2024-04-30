package com.ivanArrabe.AgenciaTurismo.dto;

import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingDto {
    private Long id;
    private User user;
    private Room room;
}
