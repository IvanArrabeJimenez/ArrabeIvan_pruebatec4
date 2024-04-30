package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public void saveRoom(Room room) {
        room.setDeleted(false);
       // room.getHotel().getRoomList().add(room);
        roomRepo.save(room);
    }

    @Override
    public Room findRoom(Long id) {
        return roomRepo.findById(id).orElse(null);
    }

    @Override
    public void editRoom(Room room, Room roomEdit) {
        room.setRoomCode(roomEdit.getRoomCode());
        room.setRoomType(roomEdit.getRoomType());
        room.setRoomCapacity(roomEdit.getRoomCapacity());
        roomRepo.save(room);
    }

    @Override
    public void logicDeleteRoom(Room room) {
        room.setDeleted(true);
        roomRepo.save(room);
    }

    @Override
    public List<RoomDto> getRooms() {
        List<Room> rooms = roomRepo.findAllByDeletedIsFalse();
        return rooms.stream()
                .map(room -> new RoomDto(room.getId(), room.getHotel().getCity(), room.getRoomCode(), room.getRoomType()))
                .toList();
    }

    @Override
    public RoomDto getRoomById(Long id) {
        return roomRepo.findAllByDeletedIsFalse().stream()
                .filter(room -> room.getId().equals(id))
                .findFirst()
                .map(room -> new RoomDto(room.getId(), room.getHotel().getCity(), room.getRoomCode(), room.getRoomType()))
                .orElse(null);
    }

    @Override
    public List<RoomDto> getAvailableRooms(LocalDate dateFrom, LocalDate dateTo, String destination) {
        return roomRepo.findAll().stream()
                // Filtrar las habitaciones cuya fecha de entrada o salida no se solape con el rango especificado
                .filter(room -> room.getRoomBookings().stream()
                        .noneMatch(booking ->
                                (booking.getEntryDate().isBefore(dateTo) || booking.getEntryDate().isEqual(dateTo)) &&
                                        (booking.getDepartureDate().isAfter(dateFrom) || booking.getDepartureDate().isEqual(dateFrom))))
                // Filtrar por la ciudad del hotel
                .filter(room -> room.getHotel().getCity().equals(destination))
                .map(room -> new RoomDto(room.getId(), room.getHotel().getCity(), room.getRoomCode(), room.getRoomType()))
                .toList();
    }
}
