package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {

    void saveFlight(Flight flight);

    void editFlight(Flight flight, Flight flightEdit);

    void logicDeleteFlight(Flight flight);

    void upgradeFlight(Flight flight, FlightBooking flightBooking, Boolean isCreating);

    List<FlightDto> getFlights();

    Flight findFlight(Long id);

    FlightDto getFlightById(Long id);

    List<FlightDto> getAvailableFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);
}
