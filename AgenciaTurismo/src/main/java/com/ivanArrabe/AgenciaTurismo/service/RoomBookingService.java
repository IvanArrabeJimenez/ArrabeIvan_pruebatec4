package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import com.ivanArrabe.AgenciaTurismo.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepo;

    @Override
    public Boolean checkAllRoomBookings(Room room) {
        List<RoomBooking> roomBookings = roomBookingRepo.findAllByRoom(room);
        return roomBookings.stream()
                .allMatch(RoomBooking::getDeleted);
    }

}
