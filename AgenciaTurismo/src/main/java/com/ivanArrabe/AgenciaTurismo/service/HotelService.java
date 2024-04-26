package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.model.Hotel;
import com.ivanArrabe.AgenciaTurismo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService implements IHotelService{

    @Autowired
    public HotelRepository hotelRepo;

    @Override
    public void saveHotel(Hotel hotel) {
        hotel.setDeleted(false);
        hotelRepo.save(hotel);
    }
}
