package com.ivanArrabe.AgenciaTurismo.controller;

import com.ivanArrabe.AgenciaTurismo.dto.RoomBookingDto;
import com.ivanArrabe.AgenciaTurismo.exception.AgenciaException;
import com.ivanArrabe.AgenciaTurismo.model.Room;
import com.ivanArrabe.AgenciaTurismo.model.RoomBooking;
import com.ivanArrabe.AgenciaTurismo.model.User;
import com.ivanArrabe.AgenciaTurismo.service.IRoomBookingService;
import com.ivanArrabe.AgenciaTurismo.service.IRoomService;
import com.ivanArrabe.AgenciaTurismo.service.IUserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/room-booking")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoomService roomService;

    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos no coinciden con la habitación solicitada."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<RoomBooking> saveRoomBooking(@RequestBody RoomBooking roomBooking) throws AgenciaException {
        Room room = roomService.findRoom(roomBooking.getRoom().getId());
        User user = userService.findUser(roomBooking.getUser().getId());

        //validamos que tenga asignado habitación y usuario
        if (user == null || user.getDeleted() || !user.getId().equals(roomBooking.getUser().getId()) ||
                room == null || room.getDeleted() || !room.getId().equals(roomBooking.getRoom().getId()) || !room.getHotel().getId().equals(roomBooking.getHotelId()) ||
                !room.getHotel().getCity().equals(roomBooking.getCity()) || !room.getRoomType().equals(roomBooking.getRoomType()) ||
                roomBooking.getEntryDate() == null || roomBooking.getDepartureDate() == null || roomBooking.getDepartureDate().isBefore(roomBooking.getEntryDate())) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            //Validamos la entrada de datos
        } else if (!room.getRoomCapacity().equals(roomBooking.getPeopleQuantity())) {
            throw new AgenciaException("La cantidad de personas no corresponde con la capacidad de la habitación.");
        } else if (room.getDeleted() || room.getHotel().getDeleted()) {
            throw new AgenciaException("La habitación o el hotel seleccionado no existe.");
        } else if (roomBookingService.checkBooking(roomBooking)) {
            throw new AgenciaException("La reserva que está intentando realizar ya existe.");
        } else if (roomBookingService.checkBookingDates(roomBooking)) {
            throw new AgenciaException("No se puede realizar la reserva porque la habitación seleccionada tiene otra reserva con esas fechas.");
        }
        //Realizamos la reserva
        roomBookingService.saveRoomBooking(room, user, roomBooking);
        return ResponseEntity.ok(roomBooking);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró la reserva que intenta eliminar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<RoomBooking> logicDeleteRoomBooking(@PathVariable Long id) {
        RoomBooking roomBooking = roomBookingService.findRoomBoking(id);
        if (roomBooking == null || roomBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        roomBookingService.logicDeleteRoomBooking(roomBooking);
        return ResponseEntity.ok(roomBooking);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<List<RoomBookingDto>> getRoomBookings() {
        if (roomBookingService.getRoomBookings().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(roomBookingService.getRoomBookings());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró ninguna reserva con el id proporcionado."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<RoomBookingDto> getRoomBookingById(@PathVariable Long id) {
        RoomBooking roomBooking = roomBookingService.findRoomBoking(id);
        if (roomBooking == null || roomBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(roomBookingService.getRoomBookingById(id));
    }

    @PutMapping("/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente."),
            @ApiResponse(responseCode = "204", description = "No se encontró la reserva que intenta editar."),
            @ApiResponse(responseCode = "400", description = "Algún parámetro no cumple con el formato o es requerido y no está presente."),
            @ApiResponse(responseCode = "500", description = "Error interno de servidor.")
    })
    public ResponseEntity<RoomBooking> editRoombooking(@PathVariable Long id, @RequestBody RoomBooking roomBookingEdit) throws AgenciaException {
        RoomBooking roomBooking = roomBookingService.findRoomBoking(id);
        Room room = roomService.findRoom(roomBookingEdit.getRoom().getId());
        User user = userService.findUser(roomBookingEdit.getUser().getId());

        //Validamos la asignación de usuario y habitación y validamos datos
        if (roomBooking == null || roomBooking.getDeleted()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (user == null || user.getDeleted()|| !user.getId().equals(roomBookingEdit.getUser().getId()) ||
                room == null || room.getDeleted() || !room.getId().equals(roomBookingEdit.getRoom().getId()) || !room.getHotel().getId().equals(roomBookingEdit.getHotelId()) ||
                !room.getHotel().getCity().equals(roomBookingEdit.getCity()) || !room.getRoomType().equals(roomBookingEdit.getRoomType()) ||
                roomBookingEdit.getEntryDate() == null || roomBookingEdit.getDepartureDate() == null || roomBookingEdit.getDepartureDate().isBefore(roomBookingEdit.getEntryDate())) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!room.getRoomCapacity().equals(roomBooking.getPeopleQuantity())) {
            throw new AgenciaException("La cantidad de personas no corresponde con la capacidad de la habitación.");
        } else if (room.getDeleted() || room.getHotel().getDeleted()) {
            throw new AgenciaException("La habitación o el hotel seleccionado no existe.");
        } else if (!roomBookingService.checkBookingDates(roomBooking)) {
            throw new AgenciaException("No se puede realizar la reserva porque la habitación seleccionada tiene otra reserva con esas fechas.");
        }
        //Editamos la reserva
        roomBookingService.editRoombooking(roomBooking, roomBookingEdit);
        return ResponseEntity.ok(roomBooking);
    }
}
