package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomBookingService {
    public Boolean checkAllRoomBookings(Room room);

}
