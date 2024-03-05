package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.street.StreetDto;
import com.yourcity.yourcity.model.entity.enums.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreetService {

    StreetDto getStreetById(Long id);

    StreetDto getStreetByName(String name);

    StreetDto createStreet(StreetDto dto);

    StreetDto updateStreet(StreetDto dto);

    void deleteStreet(Long id);

    List<StreetDto> getStreetsByType(Type type);
}
