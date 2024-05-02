package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightBookingDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;

import java.util.List;

public interface IFlightBookingService {

    void saveFlightBooking(Flight flight, User user, FlightBooking flightBooking);

    Boolean checkBooking(FlightBooking flightBooking);

    FlightBooking findFlightBooking(Long id);

    void logicDeleteFlightBooking(FlightBooking flightBooking);

    List<FlightBookingDto> getFlightBookings();

    FlightBookingDto getFlightBookingById(Long id);

    void editFlightbooking(FlightBooking flightBooking, FlightBooking flightBookingEdit);

    Boolean checkAllFlightBookings(Flight flight);
}
