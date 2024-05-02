package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomBookingDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;

import java.util.List;

public interface IRoomBookingService {

    Boolean checkAllRoomBookings(Room room);

    Boolean checkBooking(RoomBooking roomBooking);

    Boolean checkBookingDates(RoomBooking roomBooking);

    void saveRoomBooking(Room room, User user, RoomBooking roomBooking);

    RoomBooking findRoomBoking(Long id);

    void logicDeleteRoomBooking(RoomBooking roomBooking);

    List<RoomBookingDto> getRoomBookings();

    RoomBookingDto getRoomBookingById(Long id);

    void editRoombooking(RoomBooking roomBooking, RoomBooking roomBookingEdit);
}
