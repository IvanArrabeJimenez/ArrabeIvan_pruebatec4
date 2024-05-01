package com.ivanArrabe.AgenciaTurismo;

import com.ivanArrabe.AgenciaTurismo.controller.FlightController;
import com.ivanArrabe.AgenciaTurismo.controller.HotelController;
import com.ivanArrabe.AgenciaTurismo.dto.FlightDto;
import com.ivanArrabe.AgenciaTurismo.dto.HotelDto;
import com.ivanArrabe.AgenciaTurismo.service.IFlightService;
import com.ivanArrabe.AgenciaTurismo.service.IHotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AgenciaTurismoApplicationTests {

    @Mock
    private IHotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    @Mock
    private IFlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHotelsOk() {
        // Mock del servicio para devolver una lista de hoteles no vacía
        List<HotelDto> hotelList = new ArrayList<>();
        hotelList.add(new HotelDto());
        when(hotelService.getHotels()).thenReturn(hotelList);

        // Llamar al método del controlador y verificar la respuesta
        ResponseEntity<List<HotelDto>> response = hotelController.getHotels();

        // Verificar que la respuesta sea 200 OK y que la lista no esté vacía
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelList, response.getBody());
    }

    @Test
    public void testGetHotelsKo() {
        // Mock del servicio para devolver una lista de hoteles vacía
        when(hotelService.getHotels()).thenReturn(new ArrayList<>());

        // Llamar al método del controlador y verificar la respuesta
        ResponseEntity<List<HotelDto>> response = hotelController.getHotels();

        // Verificar que la respuesta sea 204 NO CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAvailableFlightsOk() {
        // Datos de entrada para el test
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(7);
        String origin = "Origen";
        String destination = "Destino";

        // Mock del servicio para devolver una lista de vuelos no vacía
        List<FlightDto> flightList = new ArrayList<>();
        flightList.add(new FlightDto());
        when(flightService.getAvailableFlights(dateFrom, dateTo, origin, destination)).thenReturn(flightList);

        // Llamar al método del controlador y verificar la respuesta
        ResponseEntity<List<FlightDto>> response = flightController.getAvailableFlights(dateFrom, dateTo, origin, destination);

        // Verificar que la respuesta sea 200 OK y que la lista no esté vacía
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flightList, response.getBody());
    }

    @Test
    public void testGetAvailableFlightsKo() {
        // Datos de entrada para el test
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(7);
        String origin = "Origen";
        String destination = "Destino";

        // Mock del servicio para devolver una lista de vuelos vacía
        when(flightService.getAvailableFlights(dateFrom, dateTo, origin, destination)).thenReturn(new ArrayList<>());

        // Llamar al método del controlador y verificar la respuesta
        ResponseEntity<List<FlightDto>> response = flightController.getAvailableFlights(dateFrom, dateTo, origin, destination);

        // Verificar que la respuesta sea 204 NO CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
