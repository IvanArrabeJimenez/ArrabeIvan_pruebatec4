package com.ivanArrabe.AgenciaTurismo.controller;

import com.ivanArrabe.AgenciaTurismo.dto.FlightBookingDto;
import com.ivanArrabe.AgenciaTurismo.exception.AgenciaException;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.model.FlightBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;
import com.ivanArrabe.AgenciaTurismo.service.IFlightBookingService;
import com.ivanArrabe.AgenciaTurismo.service.IFlightService;
import com.ivanArrabe.AgenciaTurismo.service.IUserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/agency/flight-bookings")
public class FlightBookingController {

    @Autowired
    private IFlightService flightService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFlightBookingService flightBookingService;

    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos no coinciden con el vuelo solicitado."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<FlightBooking> saveFlightBooking(@RequestBody FlightBooking flightBooking) throws AgenciaException {
        Flight flight = flightService.findFlight(flightBooking.getFlight().getId());
        User user = userService.findUser(flightBooking.getUser().getId());

        if (user == null || !user.getId().equals(flightBooking.getUser().getId()) ||
                flight == null || !flight.getId().equals(flightBooking.getFlight().getId()) || !flight.getOrigin().equals(flightBooking.getOrigin()) ||
                !flight.getDestination().equals(flightBooking.getDestination()) || !flight.getDepartureDate().equals(flightBooking.getDepartureDate())) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }else if (flight.getSeatsEconomy() == 0 && flight.getSeatsBusiness() == 0 || flight.getDeleted()) {
            throw new AgenciaException("El vuelo está completo o no existe.");
        }else if (flightBooking.getSeatsEconomyTaken() > flight.getSeatsEconomy() || flightBooking.getSeatsBusinessTaken() > flight.getSeatsBusiness()) {
            throw new AgenciaException("Ha seleccionado más asientos de los disponibles");
        }else if (flightBooking.getPeopleQuantity() != flightBooking.getSeatsEconomyTaken() + flightBooking.getSeatsBusinessTaken()) {
            throw new AgenciaException("El número de asientos seleccionados no coincide con la cantidad de personas");
        }else if (flightBookingService.checkBooking(flightBooking)) {
            throw new AgenciaException("La reserva que está intentando realizar ya existe.");
        }
        flightService.upgradeFlight(flight, flightBooking, true);
        flightBookingService.saveFlightBooking(flight, user, flightBooking);
        return ResponseEntity.ok(flightBooking);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró la reserva que intenta eliminar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<FlightBooking> logicDeleteFlightBooking(@PathVariable Long id) {

        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        if (flightBooking == null || flightBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Flight flight = flightBooking.getFlight();
        flightBookingService.logicDeleteFlightBooking(flightBooking);
        flightService.upgradeFlight(flight, flightBooking, false);
        return ResponseEntity.ok(flightBooking);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<FlightBookingDto>> getFlightBookings() {
        if (flightBookingService.getFlightBookings().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(flightBookingService.getFlightBookings());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ninguna reserva con el id proporcionado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<FlightBookingDto> getFlightBookingById(@PathVariable Long id) {
        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        if (flightBooking == null || flightBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(flightBookingService.getFlightBookingById(id));
    }

    @PutMapping("/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró el vuelo que intenta editar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<FlightBooking> editFlightbooking(@PathVariable Long id, @RequestBody FlightBooking flightBookingEdit) throws AgenciaException {
        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        Flight flight = flightService.findFlight(flightBookingEdit.getFlight().getId());
        User user = userService.findUser(flightBookingEdit.getUser().getId());
        if (flightBooking == null || flightBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (user == null || !user.getId().equals(flightBookingEdit.getUser().getId()) ||
                flight == null || !flight.getId().equals(flightBookingEdit.getFlight().getId()) || !flight.getOrigin().equals(flightBookingEdit.getOrigin()) ||
                !flight.getDestination().equals(flightBookingEdit.getDestination()) || !flight.getDepartureDate().equals(flightBookingEdit.getDepartureDate())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (flight.getSeatsEconomy() == 0 && flight.getSeatsBusiness() == 0 || flight.getDeleted()) {
            throw new AgenciaException("El vuelo está completo o no existe.");
        }
        if (flightBookingEdit.getSeatsEconomyTaken() > flight.getSeatsEconomy() || flightBookingEdit.getSeatsBusinessTaken() > flight.getSeatsBusiness()) {
            throw new AgenciaException("Ha seleccionado más asientos de los disponibles");
        }
        if (flightBookingEdit.getPeopleQuantity() != flightBookingEdit.getSeatsEconomyTaken() + flightBookingEdit.getSeatsBusinessTaken()) {
            throw new AgenciaException("El número de asientos seleccionados no coincide con la cantidad de personas");
        }

        if (flightBookingService.checkBooking(flightBookingEdit)) {
            throw new AgenciaException("La reserva que está intentando realizar ya existe.");
        }
        flightService.upgradeFlight(flight, flightBooking, false);
        flightService.upgradeFlight(flight, flightBookingEdit, true);
        flightBookingService.editFlightbooking(flightBooking, flightBookingEdit);
        return ResponseEntity.ok(flightBooking);
    }
}
