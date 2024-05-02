package com.ivanArrabe.AgenciaTurismo.controller;

import com.ivanArrabe.AgenciaTurismo.dto.RoomDto;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.service.IRoomBookingService;
import com.ivanArrabe.AgenciaTurismo.service.IRoomService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/agency/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IRoomBookingService roomBookingService;

    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        if (room == null || room.getRoomCode() == null || room.getRoomCode().isEmpty() || room.getRoomType() == null ||
                room.getRoomType().isEmpty() || room.getRoomCapacity() == null || room.getRoomCapacity() <= 0) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roomService.saveRoom(room);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró la habitación que intenta editar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Room> editRoom(@PathVariable Long id, @RequestBody Room roomEdit) {
        Room room = roomService.findRoom(id);
        if (room == null || room.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (roomEdit == null || roomEdit.getRoomCode() == null || roomEdit.getRoomCode().isEmpty() || roomEdit.getRoomType() == null ||
                roomEdit.getRoomType().isEmpty() || roomEdit.getRoomCapacity() == null || roomEdit.getRoomCapacity() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roomService.editRoom(room, roomEdit);

        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró la habitación que intenta eliminar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar la habitación porque existen reservas asociadas a ella."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<Room> logicDeleteRoom(@PathVariable Long id) {

        Room room = roomService.findRoom(id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Antes de dar de baja la habitación comprobamos que no tine reservas activas.
        if (!roomBookingService.checkAllRoomBookings(room)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //Si no hay reservas activas podemos borrar la habitación.
        roomService.logicDeleteRoom(room);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/all-rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ninguna habitación registrada."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<RoomDto>> getRooms() {
        if (roomService.getRooms().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ninguna habitación con el id proporcionado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        Room room = roomService.findRoom(id);
        if (room == null || room.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontraron habitaciones disponibles para las fechas o destinos indicados."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<RoomDto>> getAvailableRooms(@RequestParam("dateFrom") LocalDate dateFrom,
                                                           @RequestParam("dateTo") LocalDate dateTo,
                                                           @RequestParam("destination") String destination) {
        List<RoomDto> rooms = roomService.getAvailableRooms(dateFrom, dateTo, destination);

        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(rooms);
        }
    }
}
