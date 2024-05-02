package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.HotelDto;
import com.ivanArrabe.AgenciaTurismo.model.Hotel;
import com.ivanArrabe.AgenciaTurismo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    public HotelRepository hotelRepo;

    @Override
    public void saveHotel(Hotel hotel) {
        hotel.setDeleted(false);
        hotelRepo.save(hotel);
    }

    @Override
    public Hotel findHotel(Long id) {
        return hotelRepo.findById(id).orElse(null);
    }

    @Override
    public void editHotel(Hotel hotel, Hotel hotelEdit) {
        hotel.setHotelCode(hotelEdit.getHotelCode());
        hotel.setName(hotelEdit.getName());
        hotel.setCity(hotelEdit.getCity());
        hotelRepo.save(hotel);
    }

    @Override
    public void logicDeleteHotel(Hotel hotel) {
        hotel.setDeleted(true);
        hotelRepo.save(hotel);
    }

    @Override
    public List<HotelDto> getHotels() {
        return hotelRepo.findAllByDeletedIsFalse().stream()
                .map(hotel -> new HotelDto(hotel.getId(), hotel.getName(), hotel.getCity()))
                .toList();
    }

    @Override
    public HotelDto getHotelById(Long id) {
        return hotelRepo.findAllByDeletedIsFalse().stream()
                .filter(hotel -> hotel.getId().equals(id))
                .findFirst()
                .map(hotel -> new HotelDto(hotel.getId(), hotel.getName(), hotel.getCity()))
                .orElse(null);
    }
}
