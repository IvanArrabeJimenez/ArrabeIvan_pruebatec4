package com.ivanArrabe.AgenciaTurismo.controller;

import com.ivanArrabe.AgenciaTurismo.dto.FlightDto;
import com.ivanArrabe.AgenciaTurismo.model.Flight;
import com.ivanArrabe.AgenciaTurismo.service.IFlightBookingService;
import com.ivanArrabe.AgenciaTurismo.service.IFlightService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency/flights")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @Autowired
    private IFlightBookingService flightBookingService;

    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {

        //Validamos la entrada de datos
        if (flight == null || flight.getFlightCode() == null || flight.getFlightCode().isEmpty() || flight.getOrigin() == null || flight.getOrigin().isEmpty() ||
                flight.getDestination() == null || flight.getDestination().isEmpty() || flight.getDepartureDate() == null ||
                flight.getSeatsEconomy() == null || flight.getSeatsEconomy() <= 0 || flight.getSeatsBusiness() == null || flight.getSeatsBusiness() <= 0) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Guardamos el vuelo
        flightService.saveFlight(flight);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró el vuelo que intenta editar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Flight> editFlight(@PathVariable Long id, @RequestBody Flight flightEdit) {

        Flight flight = flightService.findFlight(id);
        if (flight == null || flight.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (flightEdit == null || flightEdit.getFlightCode() == null || flightEdit.getFlightCode().isEmpty() || flightEdit.getOrigin() == null || flightEdit.getOrigin().isEmpty() ||
                flightEdit.getDestination() == null || flightEdit.getDestination().isEmpty() || flightEdit.getDepartureDate() == null ||
                flightEdit.getSeatsEconomy() == null || flightEdit.getSeatsEconomy() <= 0 || flightEdit.getSeatsBusiness() == null || flightEdit.getSeatsBusiness() <= 0) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        flightService.editFlight(flight, flightEdit);

        return ResponseEntity.ok(flight);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró el vuelo que intenta eliminar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar el vuelo porque existen reservas asociadas a él."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Flight> logicDeleteFlight(@PathVariable Long id) {

        Flight flight = flightService.findFlight(id);
        if (flight == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Antes de dar de baja el vuelo comprobamos que no tiene reservas activas.
        if (!flightBookingService.checkAllFlightBookings(flight)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //Si no hay reservas activas borramos el vuelo.
        flightService.logicDeleteFlight(flight);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/all-flights")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ningún vuelo registrado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<FlightDto>> getFlights() {
        if (flightService.getFlights().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(flightService.getFlights());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ningún vuelo con el id proporcionado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.findFlight(id);
        if (flight == null || flight.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontraron vuelos para las fechas o destinos indicados."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<FlightDto>> getAvailableFlights(@RequestParam("dateFrom") LocalDate dateFrom,
                                                               @RequestParam("dateTo") LocalDate dateTo,
                                                               @RequestParam("origin") String origin,
                                                               @RequestParam("destination") String destination) {
        List<FlightDto> flights = flightService.getAvailableFlights(dateFrom, dateTo, origin, destination);

        if (flights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(flights);
        }
    }
}
