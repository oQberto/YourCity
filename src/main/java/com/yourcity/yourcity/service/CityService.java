package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.city.CityDto;
import org.springframework.stereotype.Service;

@Service
public interface CityService {

    CityDto createCity(CityDto dto);

    CityDto getCityById(Long id);

    CityDto updateCity(CityDto dto);

    void deleteCity(Long id);
}
