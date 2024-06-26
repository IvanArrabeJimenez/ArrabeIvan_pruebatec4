package com.ivanArrabe.AgenciaTurismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    private Long id;
    private String name;
    private String city;
}
