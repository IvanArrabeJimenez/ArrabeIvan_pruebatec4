package com.ivanArrabe.AgenciaTurismo.repository;

import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByDeletedFalse();

    List<FlightBooking> findAllByDeletedIsFalse();

    List<FlightBooking> findAllByFlight(Flight flight);
}
