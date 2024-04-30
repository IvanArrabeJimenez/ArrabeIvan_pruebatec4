package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.HotelDto;
import com.ivanArrabe.AgenciaTurismo.model.Hotel;

import java.util.List;

public interface IHotelService {
    public void saveHotel(Hotel hotel);

    public Hotel findHotel(Long id);

    public void editHotel(Hotel hotel, Hotel hotelEdit);

    public void logicDeleteHotel(Hotel hotel);

    public List<HotelDto> getHotels();

    public HotelDto getHotelById(Long id);
}
