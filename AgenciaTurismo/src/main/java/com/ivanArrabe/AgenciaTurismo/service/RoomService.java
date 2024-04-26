package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public void saveRoom(Room room) {
        roomRepo.save(room);
    }
}
