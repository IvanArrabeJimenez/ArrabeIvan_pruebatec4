package com.ivanArrabe.AgenciaTurismo.repository;

import com.ivanArrabe.AgenciaTurismo.dto.RoomBookingDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findAllByRoom(Room room);

    List<RoomBooking> findByDeletedFalse();

    List<RoomBooking> findAllByDeletedIsFalse();
}
