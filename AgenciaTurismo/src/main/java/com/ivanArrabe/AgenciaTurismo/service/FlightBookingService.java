package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.FlightbookingDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;
import com.ivanArrabe.AgenciaTurismo.repository.FlightBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightBookingService implements IFlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    @Override
    public void saveFlightBooking(Flight flight, User user, FlightBooking flightBooking) {
        flightBooking.setFlight(flight);
        flightBooking.setUser(user);
        flightBookingRepo.save(flightBooking);
    }

    @Override
    public Boolean checkBooking(FlightBooking flightBooking) {
        flightBooking.setDeleted(false);
        // Obtenemos la lista de reservas existentes que no estén eliminadas
        List<FlightBooking> flightBookings = flightBookingRepo.findByDeletedFalse();

        // Buscamos si existe una reserva completamente igual a la que vamos a introducir
        return flightBookings.stream()
                .anyMatch(existingBooking ->
                        existingBooking.getUser().getId().equals(flightBooking.getUser().getId()) &&
                                existingBooking.getFlight().getId().equals(flightBooking.getFlight().getId()) &&
                                existingBooking.getPeopleQuantity().equals(flightBooking.getPeopleQuantity()) &&
                                existingBooking.getOrigin().equals(flightBooking.getOrigin()) &&
                                existingBooking.getDestination().equals(flightBooking.getDestination()) &&
                                existingBooking.getDepartureDate().equals(flightBooking.getDepartureDate()) &&
                                existingBooking.getSeatsEconomyTaken().equals(flightBooking.getSeatsEconomyTaken()) &&
                                existingBooking.getSeatsBusinessTaken().equals(flightBooking.getSeatsBusinessTaken()));
    }

    @Override
    public FlightBooking findFlightBooking(Long id) {
        return flightBookingRepo.findById(id).orElse(null);
    }

    @Override
    public void logicDeleteFlightBooking(FlightBooking flightBooking) {
        flightBooking.setDeleted(true);
        flightBookingRepo.save(flightBooking);
    }

    @Override
    public List<FlightbookingDto> getFlightBookings() {
        List<FlightBooking> flightBookings = flightBookingRepo.findAllByDeletedIsFalse();

        return flightBookings.stream()
                .map(flightBooking -> new FlightbookingDto(flightBooking.getId(), flightBooking.getUser(), flightBooking.getFlight()))
                .toList();
    }

    @Override
    public FlightbookingDto getFlightBookingById(Long id) {
        return flightBookingRepo.findAllByDeletedIsFalse().stream()
                .filter(flightBooking -> flightBooking.getId().equals(id))
                .findFirst()
                .map(flightBooking -> new FlightbookingDto(flightBooking.getId(), flightBooking.getUser(), flightBooking.getFlight()))
                .orElse(null);
    }

    @Override
    public void editFlightbooking(FlightBooking flightBooking, FlightBooking flightBookingEdit) {
        flightBooking.setUser(flightBookingEdit.getUser());
        flightBooking.setFlight(flightBookingEdit.getFlight());
        flightBooking.setOrigin(flightBookingEdit.getOrigin());
        flightBooking.setDestination(flightBookingEdit.getDestination());
        flightBooking.setDepartureDate(flightBookingEdit.getDepartureDate());
        flightBooking.setSeatsEconomyTaken(flightBookingEdit.getSeatsEconomyTaken());
        flightBooking.setSeatsBusinessTaken(flightBookingEdit.getSeatsBusinessTaken());
        flightBooking.setPeopleQuantity(flightBooking.getPeopleQuantity());
        flightBookingRepo.save(flightBooking);
    }

    @Override
    public Boolean checkAllFlightBookings(Flight flight) {
        List<FlightBooking> flightBookings = flightBookingRepo.findAllByFlight(flight);
        return flightBookings.stream()
                .allMatch(FlightBooking::getDeleted);
    }
}
