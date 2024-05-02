package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.HotelDto;
import com.ivanArrabe.AgenciaTurismo.model.Hotel;

import java.util.List;

public interface IHotelService {

    void saveHotel(Hotel hotel);

    Hotel findHotel(Long id);

    void editHotel(Hotel hotel, Hotel hotelEdit);

    void logicDeleteHotel(Hotel hotel);

    List<HotelDto> getHotels();

    HotelDto getHotelById(Long id);
}
