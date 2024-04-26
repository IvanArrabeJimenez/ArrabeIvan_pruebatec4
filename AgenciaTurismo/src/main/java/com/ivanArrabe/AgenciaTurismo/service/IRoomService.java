package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    public void saveRoom(Room room);

    Room findRoom(Long id);

    void editRoom(Room room, Room roomEdit);

    void logicDeleteRoom(Room room);

    List<RoomDto> getRooms();

    RoomDto getRoomById(Long id);

    List<RoomDto> getAvailableRooms(LocalDate dateFrom, LocalDate dateTo, String destination);
}
