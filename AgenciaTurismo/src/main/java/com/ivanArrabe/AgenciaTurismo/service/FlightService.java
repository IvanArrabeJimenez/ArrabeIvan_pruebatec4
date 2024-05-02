package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import com.ivanArrabe.AgenciaTurismo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository flightRepo;

    @Override
    public void saveFlight(Flight flight) {
        flight.setDeleted(false);
        flightRepo.save(flight);
    }

    @Override
    public void editFlight(Flight flight, Flight flightEdit) {
        flight.setFlightCode(flightEdit.getFlightCode());
        flight.setOrigin(flightEdit.getOrigin());
        flight.setDestination(flightEdit.getDestination());
        flight.setDepartureDate(flightEdit.getDepartureDate());
        flight.setSeatsEconomy(flightEdit.getSeatsEconomy());
        flight.setSeatsBusiness(flightEdit.getSeatsBusiness());
        flightRepo.save(flight);
    }

    @Override
    public void logicDeleteFlight(Flight flight) {
        flight.setDeleted(true);
        flightRepo.save(flight);
    }

    @Override
    public List<FlightDto> getFlights() {

        return flightRepo.findAllByDeletedIsFalse().stream()
                .map(flight -> new FlightDto(flight.getId(), flight.getFlightCode(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate()))
                .toList();
    }

    @Override
    public Flight findFlight(Long id) {
        return flightRepo.findById(id).orElse(null);
    }

    @Override
    public FlightDto getFlightById(Long id) {
        return flightRepo.findAllByDeletedIsFalse().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .map(flight -> new FlightDto(flight.getId(), flight.getFlightCode(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate()))
                .orElse(null);
    }

    @Override
    public void upgradeFlight(Flight flight, FlightBooking flightBooking, Boolean isCreating) {
        //En este método accedemos al vuelo para restarle asientos o sumárselos en función de si creamos reserva o la eliminamos/editamos.
        if (isCreating) {
            flight.setSeatsEconomy(flight.getSeatsEconomy() - flightBooking.getSeatsEconomyTaken());
            flight.setSeatsBusiness(flight.getSeatsBusiness() - flightBooking.getSeatsBusinessTaken());
            if (flight.getSeatsEconomy() == 0 && flight.getSeatsBusiness() == 0) {
                logicDeleteFlight(flight);
            }
        } else {
            flight.setSeatsEconomy(flight.getSeatsEconomy() + flightBooking.getSeatsEconomyTaken());
            flight.setSeatsBusiness(flight.getSeatsBusiness() + flightBooking.getSeatsBusinessTaken());
        }
        flightRepo.save(flight);
    }

    @Override
    public List<FlightDto> getAvailableFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {

        // Consulta para obtener los vuelos de ida. En el nombre del método va la query con los parámetros que queremos mandar por medio de hibernate
        List<Flight> departureFlight = flightRepo.findFlightsByDepartureDateAndOriginAndDestinationAndDeletedIsFalse(dateFrom, origin, destination);
        // Consulta para obtener los vuelos de vuelta (intercambiando origen y destino)
        List<Flight> returnFlight = flightRepo.findFlightsByDepartureDateAndOriginAndDestinationAndDeletedIsFalse(dateTo, destination, origin);

        // Combinamos los vuelos en una lista para devolver y mostrar al usuario
        List<Flight> availableFlights = new ArrayList<>();
        availableFlights.addAll(departureFlight);
        availableFlights.addAll(returnFlight);

        return availableFlights.stream()
                .map(flight -> new FlightDto(flight.getId(), flight.getFlightCode(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate()))
                .toList();
    }


}
