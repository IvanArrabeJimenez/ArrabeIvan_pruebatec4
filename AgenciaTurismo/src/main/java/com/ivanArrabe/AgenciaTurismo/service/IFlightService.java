package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    public void saveFlight(Flight flight);
    public void editFlight(Flight flight, Flight flightEdit);
    public void logicDeleteFlight(Flight flight);
    public void upgradeFlight(Flight flight, FlightBooking flightBooking, Boolean isCreating);
    public List<FlightDto> getFlights();
    public Flight findFlight(Long id);
    public FlightDto getFlightById(Long id);
    public List <FlightDto> getAvailableFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);
}
