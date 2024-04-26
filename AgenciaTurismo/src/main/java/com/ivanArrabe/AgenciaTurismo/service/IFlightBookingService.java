package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightbookingDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;

import java.util.List;

public interface IFlightBookingService {
    public void saveFlightBooking(Flight flight, User user, FlightBooking flightBooking);

    public Boolean checkBooking(FlightBooking flightBooking);

    public FlightBooking findFlightBooking(Long id);

    public void logicDeleteFlightBooking(FlightBooking flightBooking);

    public List<FlightbookingDto> getFlightBookings();

    public FlightbookingDto getFlightBookingById(Long id);

    public void editFlightbooking(FlightBooking flightBooking, FlightBooking flightBookingEdit);

    public Boolean checkAllFlightBookings(Flight flight);
}
