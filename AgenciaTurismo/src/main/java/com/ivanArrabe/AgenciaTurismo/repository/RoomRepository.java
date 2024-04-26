package com.ivanArrabe.AgenciaTurismo.repository;

import com.ivanArrabe.AgenciaTurismo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByDeletedIsFalse();

    List<Room> findRoomsByDepartureDateBeforeAndEntryDateAfterOrEntryDateIsNullOrDepartureDateIsNull(LocalDate dateTo, LocalDate dateFrom);
}
