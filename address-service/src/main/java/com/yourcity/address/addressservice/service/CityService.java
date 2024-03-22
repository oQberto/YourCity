package com.yourcity.address.addressservice.service;

import com.yourcity.address.addressservice.model.dto.city.CityDto;
import org.springframework.stereotype.Service;

@Service
public interface CityService {

    CityDto createCity(CityDto dto);

    CityDto getCityById(Long id);

    CityDto updateCity(CityDto dto);

    void deleteCity(Long id);
}
