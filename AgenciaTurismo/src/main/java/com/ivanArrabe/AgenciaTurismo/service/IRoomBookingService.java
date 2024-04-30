package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomBookingDto;
import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;

import java.time.LocalDate;
import java.util.List;

public interface IRoomBookingService {
    public Boolean checkAllRoomBookings(Room room);

    public Boolean checkBooking(RoomBooking roomBooking);

    Boolean checkBookingDates(RoomBooking roomBooking);

    public void saveRoomBooking(Room room, User user, RoomBooking roomBooking);

    public RoomBooking findRoomBoking(Long id);

    public void logicDeleteRoomBooking(RoomBooking roomBooking);

    List <RoomBookingDto> getRoomBookings();

    public RoomBookingDto getRoomBookingById(Long id);

    public void editRoombooking(RoomBooking roomBooking, RoomBooking roomBookingEdit);
}
