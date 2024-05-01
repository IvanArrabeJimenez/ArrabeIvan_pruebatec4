package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomBookingDto;
import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;
import com.ivanArrabe.AgenciaTurismo.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public Boolean checkBooking(RoomBooking roomBooking) {
        roomBooking.setDeleted(false);

        return roomBookingRepo.findByDeletedFalse().stream()
                .anyMatch(existingBooking->
                        existingBooking.getUser().getId().equals(roomBooking.getUser().getId()) &&
                                existingBooking.getRoom().getId().equals(roomBooking.getRoom().getId()) &&
                                existingBooking.getPeopleQuantity().equals(roomBooking.getPeopleQuantity()) &&
                                existingBooking.getDepartureDate().equals(roomBooking.getDepartureDate()) &&
                                existingBooking.getEntryDate().equals(roomBooking.getEntryDate()) &&
                                existingBooking.getCity().equals(roomBooking.getCity()) &&
                                existingBooking.getHotelId().equals(roomBooking.getHotelId()) &&
                                existingBooking.getRoomType().equals(roomBooking.getRoomType()));
    }

    @Override
    public Boolean checkBookingDates(RoomBooking roomBooking) {

        return roomBookingRepo.findAllByRoomIdAndDeletedIsFalse(roomBooking.getRoom().getId()).stream()
                .anyMatch(existingBooking -> datesOverlap(existingBooking.getEntryDate(), existingBooking.getDepartureDate(),
                        roomBooking.getEntryDate(), roomBooking.getDepartureDate()));
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    @Override
    public void saveRoomBooking(Room room, User user, RoomBooking roomBooking) {
        roomBooking.setUser(user);
        roomBooking.setRoom(room);
        roomBookingRepo.save(roomBooking);
    }

    @Override
    public void logicDeleteRoomBooking(RoomBooking roomBooking) {
        roomBooking.setDeleted(true);
        roomBookingRepo.save(roomBooking);
    }

    @Override
    public List<RoomBookingDto> getRoomBookings() {
        return roomBookingRepo.findAllByDeletedIsFalse().stream()
                .map(roomBooking -> new RoomBookingDto(roomBooking.getId(),roomBooking.getUser(),roomBooking.getRoom()))
                .toList();
    }

    @Override
    public RoomBooking findRoomBoking(Long id) {
        return roomBookingRepo.findById(id).orElse(null);
    }

    @Override
    public RoomBookingDto getRoomBookingById(Long id) {
        return roomBookingRepo.findAllByDeletedIsFalse().stream()
                .filter(roomBooking -> roomBooking.getId().equals(id))
                .findFirst()
                .map(roomBooking -> new RoomBookingDto(roomBooking.getId(),roomBooking.getUser(),roomBooking.getRoom()))
                .orElse(null);
    }

    @Override
    public void editRoombooking(RoomBooking roomBooking, RoomBooking roomBookingEdit) {
        roomBooking.setUser(roomBookingEdit.getUser());
        roomBooking.setRoom(roomBookingEdit.getRoom());
        roomBooking.setCity(roomBookingEdit.getCity());
        roomBooking.setRoomType(roomBookingEdit.getCity());
        roomBooking.setPeopleQuantity(roomBookingEdit.getPeopleQuantity());
        roomBooking.setEntryDate(roomBookingEdit.getEntryDate());
        roomBooking.setDepartureDate(roomBookingEdit.getDepartureDate());
        roomBooking.setHotelId(roomBookingEdit.getHotelId());
        roomBookingRepo.save(roomBooking);
    }

}
