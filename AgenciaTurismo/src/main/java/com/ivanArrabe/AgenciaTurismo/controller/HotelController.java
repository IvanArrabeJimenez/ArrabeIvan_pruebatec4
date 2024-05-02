package com.ivanArrabe.AgenciaTurismo.controller;

import com.ivanArrabe.AgenciaTurismo.dto.HotelDto;
import com.ivanArrabe.AgenciaTurismo.model.Hotel;
import com.ivanArrabe.AgenciaTurismo.service.IHotelService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/hotels")
public class HotelController {

    @Autowired
    public IHotelService hotelService;

    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        if (hotel == null || hotel.getHotelCode() == null || hotel.getHotelCode().isEmpty() ||
                hotel.getName() == null || hotel.getName().isEmpty() ||
                hotel.getCity() == null || hotel.getCity().isEmpty()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        hotelService.saveHotel(hotel);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró el hotel que intenta editar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Hotel> editHotel(@PathVariable Long id, @RequestBody Hotel hotelEdit) {
        Hotel hotel = hotelService.findHotel(id);
        if (hotel == null || hotel.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (hotelEdit == null || hotelEdit.getHotelCode() == null || hotelEdit.getHotelCode().isEmpty() ||
                hotelEdit.getName() == null || hotelEdit.getName().isEmpty() ||
                hotelEdit.getCity() == null || hotelEdit.getCity().isEmpty()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        hotelService.editHotel(hotel, hotelEdit);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró el hotel que intenta eliminar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Hotel> logicDeleteHotel(@PathVariable Long id) {
        Hotel hotel = hotelService.findHotel(id);
        if (hotel == null || hotel.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        hotelService.logicDeleteHotel(hotel);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ningún hotel registrado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<HotelDto>> getHotels() {
        if (hotelService.getHotels().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(hotelService.getHotels());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ningún hotel con el id proporcionado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.findHotel(id);
        if (hotel == null || hotel.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }
}
